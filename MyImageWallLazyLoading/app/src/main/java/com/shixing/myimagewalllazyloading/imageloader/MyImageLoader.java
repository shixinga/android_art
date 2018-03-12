package com.shixing.myimagewalllazyloading.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.shixing.myimagewalllazyloading.MainActivity;
import com.shixing.myimagewalllazyloading.R;
import com.shixing.myimagewalllazyloading.utils.MyUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shixing on 2017/11/24.
 */

public class MyImageLoader {
    private Context mContext;
    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;
    private static final int TAG_KEY_URI = R.id.imageloader_uri;
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int DISK_CACHE_INDEX = 0;
    private boolean mIsDiskLruCacheCreated = false;

    private MyImageLoader(Context context) {
        mContext = context;
        int maxMemorySize = (int) Runtime.getRuntime().maxMemory() / 1024; //转换成KB
        int cacheMemorySize = maxMemorySize / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheMemorySize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

        File fileDiskDir = getDiskCacheFile("bitmapaa");
        if (!fileDiskDir.exists()) {
            fileDiskDir.mkdir();
        }

        if (getUsableSpace(fileDiskDir) >= DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(fileDiskDir, 1, 1, DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static MyImageLoader build(Context context) {
        return new MyImageLoader(context);
    }

    //AsyncTask ThreadPool 的模拟（说白了就是copy）
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    private static ThreadFactory sThreadFactory = new ThreadFactory() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "imageloader thread# " + atomicInteger.getAndIncrement());
        }
    };
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(128),
            sThreadFactory);


    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            LoadResult result = (LoadResult) msg.obj;
            ImageView iv = result.iv;
            Log.d(MainActivity.TAG, "handleMessage: " + result.bitmap);
            if (result.uri.equals(iv.getTag(TAG_KEY_URI))) {
                if (result.bitmap != null) {
                    iv.setImageBitmap(result.bitmap);
                }
            } else {
                Log.d(MainActivity.TAG, "handleMessage:set image bitmap,but url has changed, ignored!");
            }
        }
    };



    public void bindBitmap(final ImageView imageView, final String uri, final int reqWidth, final int reqHeight) {
        imageView.setTag(TAG_KEY_URI, uri);
        String key = MyUtils.hashKeyFormUrl(uri); //key 就是uri对应的md5编码
        Bitmap bitmap = null;
        bitmap = getBitmapFromCache(key);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmapWithTime = loadBitmap(uri, reqWidth, reqHeight);
                LoadResult result = new LoadResult(imageView, uri, bitmapWithTime);
                mHandler.obtainMessage(0,result).sendToTarget();
            }
        };

        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    private Bitmap loadBitmap(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap = null;
        String key = MyUtils.hashKeyFormUrl(uri); //key 是uri对应的md5的编码

        //从内存中取
        bitmap = getBitmapFromCache(key);
        if (bitmap != null) {
            return bitmap;
        }

        //从外存（sd卡中）取
        try {
            bitmap = getBitmapFromDiskCache(key, reqWidth, reqHeight); //ifbitmap不为空，则会将bitmap压缩后放入内存缓存
            if (bitmap != null) {
                return bitmap;
            }

            bitmap = getBitmapFromHttp(uri, reqWidth, reqHeight);
            if (bitmap != null) {
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = getBitmapFromUrlDirectly(uri);
        return bitmap;
    }

    private Bitmap getBitmapFromUrlDirectly(String uri) {
        URL url;
        Bitmap bitmap = null;
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bis = null;
        try {
            url = new URL(uri);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            bis = new BufferedInputStream(httpURLConnection.getInputStream());
            bitmap = BitmapFactory.decodeStream(bis);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bis != null) {
                MyUtils.close(bis);
            }
        }
        return bitmap;
    }

    private Bitmap getBitmapFromHttp(String uri, int reqWidth, int reqHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("connect network on UI thread!");
        }
        if (mDiskLruCache == null) {
            return null;
        }
        String key = MyUtils.hashKeyFormUrl(uri);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
//        Log.d(MainActivity.TAG, "getBitmapFromHttp: " + editor);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (downloadFromUrl(uri, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return getBitmapFromDiskCache(key, reqWidth, reqHeight);
    }

    private boolean downloadFromUrl(String uri, OutputStream outputStream) {
        URL url;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(uri);
            bos = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);
            urlConnection = (HttpURLConnection) url.openConnection();
            bis = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);

            int content;
            while ((content = bis.read()) != -1) {
                bos.write(content);
            }
//            Log.d(MainActivity.TAG, "downloadFromUrl: succeed");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            MyUtils.close(bos);
            MyUtils.close(bis);
        }
//        Log.d(MainActivity.TAG, "downloadFromUrl: fail");
        return false;
    }

    private Bitmap getBitmapFromDiskCache(String key, int reqWidth, int reqHeight) throws IOException {
        Bitmap bitmap = null;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.d(MainActivity.TAG, "getBitmapFromDiskCache: load bitmap from disk on mainThread");
        }
        if (mDiskLruCache == null) {
            return null;
        }
        DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
        if (snapShot != null) {
            FileInputStream fileInputStream = (FileInputStream) snapShot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fd = fileInputStream.getFD();
            bitmap = ImageResizer.decodeSampleBitmapFromFileDescriptor(fd, reqWidth, reqHeight);
            if (bitmap != null) {

                addBitmapToCache(key, bitmap);
            }
        }
//        Log.d(MainActivity.TAG, "getBitmapFromDiskCache: " + bitmap);
        return bitmap;
    }


    public Bitmap getBitmapFromCache(String key) {
        return mMemoryCache.get(key);
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }


    private long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }

    public File getDiskCacheFile(String uniqueName) {
        boolean isExternalStorageAvailable = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        String parentPath = null;
        if (isExternalStorageAvailable) {
            parentPath = mContext.getExternalCacheDir().getPath();
            Log.d(MainActivity.TAG, "getDiskCacheFile: externalPath=" + parentPath);
        } else {
            parentPath = mContext.getCacheDir().getPath();
        }
        Log.d(MainActivity.TAG, "getDiskCacheFile: cachePath=" + mContext.getCacheDir().getPath());
        return new File(parentPath + File.separator + uniqueName);
    }

    class LoadResult {

        public LoadResult(ImageView iv, String uri, Bitmap bitmap) {
            this.iv = iv;
            this.uri = uri;
            this.bitmap = bitmap;
        }

        ImageView iv;
        String uri;
        Bitmap bitmap;
    }
}
