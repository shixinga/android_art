package com.example.csx.myimagewall.loader;

import android.content.Context;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.csx.myimagewall.MainActivity;
import com.example.csx.myimagewall.R;
import com.example.csx.myimagewall.utils.MyUtils;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shixing on 2017/11/22.
 */

public class MyImageLoader {

    public static final int MESSAGE_POST_RESULT = 1;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;

    private static final int TAG_KEY_URI = R.id.imageloader_uri;
    private static final int DISK_CHANGE_SIZE = 1024 * 1024 * 50; //50M
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int DISK_CHANGE_INDEX = 0;
    private boolean mIsDiskLruCacheCreated = false;



    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "ImageLoader##" + mCount.getAndIncrement());
        }
    };
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
          CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
            KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),sThreadFactory
        );

    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.iv;
            String uri = (String) imageView.getTag(TAG_KEY_URI);
//            Log.d(MainActivity.TAG, "handleMessage:uri= " + uri + "----resultUri=" + result.url);
            if (uri.equals(result.url)) {
                imageView.setImageBitmap(result.bitmap);
            } else {
                Log.d(MainActivity.TAG, "handleMessage: set image bitmap,but url has changed, ignored");
            }
        }
    };

    private Context mContext;
    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private MyImageLoader(Context context) {
        mContext = context.getApplicationContext();
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        Log.d(MainActivity.TAG, "MyImageLoader: cacheSize" + cacheSize);
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int result = value.getRowBytes() * value.getHeight() / 1024;
                Log.d(MainActivity.TAG, "sizeOf: " + result);
                return result;
            }
        };


        File diskCacheDir = getDiskCacheDir(mContext, "bitmap");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        if (getUsableSpace(diskCacheDir) > DISK_CHANGE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CHANGE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static MyImageLoader build(Context context) {
        return new MyImageLoader(context);
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitMapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
        Log.d(MainActivity.TAG, "addBitmapToMemoryCache: has exited?="+getBitMapFromMemoryCache(key) + " key=" + key);
    }

    private Bitmap getBitMapFromMemoryCache(String key) {
        Log.d(MainActivity.TAG, "getBitMapFromMemoryCache: " + key);
        return mMemoryCache.get(key);
    }

    public void bindBitmap(final String uri, final ImageView imageView,final int reqWidth, final int reqHeight) {
        imageView.setTag(TAG_KEY_URI, uri);
        final Bitmap bitmap = loadBitmapFromMemCache(uri);
        //如果内存缓存有，就返回
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return ;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap1 = laodBitmap(uri, reqWidth, reqHeight);
                //如果sd卡有缓存
                if (bitmap1 != null) {
                    LoaderResult result = new LoaderResult(imageView, uri, bitmap1);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
                }
            }
        };

        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    private Bitmap laodBitmap(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null) {
            Log.d(MainActivity.TAG, "laodBitmap: loadBitmapFromCache,uri= " + uri);
            return bitmap;
        }
        try {
            bitmap = loadBitmapFromDiskCache(uri, reqWidth, reqHeight);
            if (bitmap != null) {
//                Log.d(MainActivity.TAG, "laodBitmap: loadBitmapFromDisk,url=" + uri);
                return bitmap;
            }

            bitmap = loadBitmapFromHttp(uri, reqWidth, reqHeight);
//            Log.d(MainActivity.TAG, "laodBitmap: loadBitmapFromHttp,url=" + uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap == null && !mIsDiskLruCacheCreated) {
            Log.d(MainActivity.TAG, "laodBitmap: encounter error,DiskLruCache is not created !");
            bitmap = downloadBitmapFromUrl(uri);
        }

        return bitmap;
    }

    private Bitmap loadBitmapFromDiskCache(String uri, int reqWidth, int reqHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.d(MainActivity.TAG, "loadBitmapFromDiskCache: loadbitmap from UI thread,not recommended!");
        }
        if (mDiskLruCache == null) {
            return null;
        }

        Bitmap bitmap = null;
        String key = hashKeyFromUrl(uri);
        DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
        if (snapShot != null) {
            FileInputStream fileInputStream = (FileInputStream) snapShot.getInputStream(DISK_CHANGE_INDEX);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            bitmap = ImageResizer.decodeSampleBitmapFromFileDescripter(fileDescriptor, reqWidth, reqHeight);
            if (bitmap != null) {
                addBitmapToMemoryCache(key, bitmap); //从198行得出：缓存的bitmap都是被压缩过得
            }
        }
        Log.d(MainActivity.TAG, "loadBitmapFromDiskCache:bitmap= " + bitmap);

        return bitmap;
    }

    //网上下载图片到sd卡上
    private Bitmap loadBitmapFromHttp(String uri, int reqWidth, int reqHeight) throws IOException {
        Log.d(MainActivity.TAG, "loadBitmapFromHttp: ");
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw  new RuntimeException("can not visit network from UI thread");
        }

        if (mDiskLruCache == null) {
            return null;
        }

        String key = hashKeyFromUrl(uri);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(DISK_CHANGE_INDEX);
            if (downloadUrlToStream(uri, outputStream)) {
                editor.commit();
                Log.d(MainActivity.TAG, "loadBitmapFromHttp: succeed");
            } else {
                editor.abort();
                Log.d(MainActivity.TAG, "loadBitmapFromHttp: fail");
            }
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskCache(uri, reqWidth, reqHeight);
    }

    private boolean downloadUrlToStream(String uri, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (uri != null) {
                urlConnection.disconnect();
            }
            MyUtils.close(out);
            MyUtils.close(in);
        }

        return false;
    }


    //why 不被压缩一下再放入内存
    private Bitmap downloadBitmapFromUrl(String uri) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            MyUtils.close(in);
        }
        return bitmap;
    }
    private Bitmap loadBitmapFromMemCache(String uri) {
        final String key = hashKeyFromUrl(uri);
        Bitmap bitmap = getBitMapFromMemoryCache(key);
        return bitmap;
    }

    private String hashKeyFromUrl(String uri) {
        String cacheKey;
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(uri.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(uri.hashCode());
        }

        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private long getUsableSpace(File diskCacheDir) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return diskCacheDir.getUsableSpace();
        }
        final StatFs stats = new StatFs(diskCacheDir.getPath());

        return stats.getBlockSize() * stats.getAvailableBlocks();
    }

    private File getDiskCacheDir(Context mContext, String uniqueName) {
        boolean externalStorageAvailable = Environment.getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = mContext.getExternalCacheDir().getPath();
        } else {
            cachePath = mContext.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + uniqueName);
    }

    class LoaderResult {
        ImageView iv;
        String url;
        Bitmap bitmap;

        public LoaderResult(ImageView iv, String url, Bitmap bitmap) {
            this.iv = iv;
            this.url = url;
            this.bitmap = bitmap;
        }
    }
}
