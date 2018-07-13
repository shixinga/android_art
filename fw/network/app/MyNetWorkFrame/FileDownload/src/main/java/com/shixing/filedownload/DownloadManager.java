package com.shixing.filedownload;

import android.util.Log;

import com.shixing.filedownload.db.DownloadEntity;
import com.shixing.filedownload.db.DownloadHelper;
import com.shixing.filedownload.file.FileStorageManager;
import com.shixing.filedownload.http.DownloadCallback;
import com.shixing.filedownload.http.HttpManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import android.os.Process;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by shixing on 2018/7/7.
 */

public class DownloadManager {
    public final static int MAX_THREAD = 3;
    public final static int LOCAL_PROGRESS_SIZE = 1;

    //    private static final DownloadManager sManager = new DownloadManager();
    private static volatile DownloadManager sManager;

    private static ExecutorService sLocalProgressPool = Executors.newFixedThreadPool(LOCAL_PROGRESS_SIZE);

    private static ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, 60, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {

        private AtomicInteger mInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "download thread #" + mInteger.getAndIncrement());
            return thread;
        }
    });

    private HashSet<DownloadTask> mDownloadTaskSet = new HashSet<>();

    private List<DownloadEntity> mDownloadEntities;
    private long mLength;

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        if (sManager == null) {
            synchronized (DownloadManager.class) {
                if (sManager == null) {
                    sManager = new DownloadManager();
                }
            }
        }
        return sManager;
    }

    private void finish(DownloadTask task) {
        mDownloadTaskSet.remove(task);
    }
    public void download(final String url, final DownloadCallback callback) {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND); //这行代码是让后台线程的优先级变低，从而ui线程优先级就会变高
        final DownloadTask downloadTask = new DownloadTask(url, callback);
        if (mDownloadTaskSet.contains(downloadTask)) { //是为了防止用户重复点击正在下载的文件
            callback.fail(HttpManager.TASK_RUNNING_ERROR_CODE, "你的下载任务已经在执行了");
            return;
        }
        mDownloadTaskSet.add(downloadTask);
        mDownloadEntities = DownloadHelper.getInstance().getAll(url);
        if (mDownloadEntities == null || mDownloadEntities.size() == 0) {

            HttpManager.getInstance().asyncRequestGetFileLength(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    finish(downloadTask);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (!response.isSuccessful() && callback != null) {
                        callback.fail(HttpManager.NETWORK_ERROR_CODE, "网络有问题");
                        return;
                    }
                    String lenStr = response.header("Content-Length");
                    if (lenStr == null || "".equals(lenStr)) {
                        Log.d("main", "onResponse 文件长度获取失败");
                        return;
                    }
                    mLength = Integer.parseInt(lenStr);
                    Log.d("main", "onResponse: total length=" + mLength + ", header length=" + lenStr);
                    if (mLength == -1) {
                        callback.fail(HttpManager.CONTENT_LENGTH_ERROR_CODE, "content length = -1");
                        return;
                    }
                    processDownload(url, mLength, callback);

                    finish(downloadTask);
                }
            });
        } else {
            for (int i = 0; i < mDownloadEntities.size(); i++) {
                DownloadEntity entity = mDownloadEntities.get(i);
                if (i == mDownloadEntities.size() - 1) {
                    mLength = entity.getEnd_position() + 1;
                }
                long startSize = entity.getStart_position() + entity.getProgress_position();
                long endSize = entity.getEnd_position();
                sThreadPool.execute(new DownloadRunnable(startSize, endSize, url, callback, entity));
            }
        }

        sLocalProgressPool.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    File file = FileStorageManager.getInstance().getFileByName(url); //正在下载的文件
                    long currentFileSize = file.length();
                    int progress = (int) (100.0 * currentFileSize / mLength);
                    if (progress >= 100) {
                        callback.progress(progress);
                        return;
                    }
                    callback.progress(progress);
                }
            }
        });
    }

    private void processDownload(String url, long length, DownloadCallback callback) {
        if (mDownloadEntities == null || mDownloadEntities.size() == 0) {
            mDownloadEntities = new ArrayList<>();
        }
        long threadDownloadSize = length / MAX_THREAD;
        for (int i = 0; i < MAX_THREAD; ++i) {
            DownloadEntity entity = new DownloadEntity();
            long startSize = i * threadDownloadSize;
            long endSize = 0;
            if (i == MAX_THREAD - 1) {
                endSize = length - 1;
            } else {
                endSize = (i + 1) * threadDownloadSize - 1;
            }

            entity.setDownload_url(url);
            entity.setStart_position(startSize);
            entity.setEnd_position(endSize);
            entity.setThread_id(i + 1);
            sThreadPool.execute(new DownloadRunnable(startSize, endSize, url, callback, entity));
        }

    }
}
