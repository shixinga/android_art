package com.shixing.filedownload;

import com.shixing.filedownload.db.DownloadEntity;
import com.shixing.filedownload.db.DownloadHelper;
import com.shixing.filedownload.file.FileStorageManager;
import com.shixing.filedownload.http.DownloadCallback;
import com.shixing.filedownload.http.HttpManager;
import com.shixing.filedownload.utils.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

/**
 * Created by shixing on 2018/7/7.
 */

public class DownloadRunnable implements Runnable {

    private long mStart;

    private long mEnd;

    private String mUrl;

    private DownloadCallback mCallback;

    private DownloadEntity mEntity;

    public DownloadRunnable(long mStart, long mEnd, String mUrl, DownloadCallback mCallback, DownloadEntity mEntity) {
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mUrl = mUrl;
        this.mCallback = mCallback;
        this.mEntity = mEntity;
    }

    public DownloadRunnable(long mStart, long mEnd, String mUrl, DownloadCallback mCallback) {
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mUrl = mUrl;
        this.mCallback = mCallback;
    }

    @Override
    public void run() {
        Response response = HttpManager.getInstance().syncRequest(mUrl, mStart, mEnd);
        if (response == null && mCallback != null) {
            mCallback.fail(HttpManager.NETWORK_ERROR_CODE, "网络出问题了");
            return;
        }
        File file = FileStorageManager.getInstance().getFileByName(mUrl);
        long lastFinshProgress = mEntity.getProgress_position() == null ? 0 : mEntity.getProgress_position();
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(mStart);
            byte[] buffer = new byte[1024 * 500];
            int len;
            long progress = 0;
            InputStream inputStream = response.body().byteStream();
            Logger.debug("main", "start progress--------->" + mStart + ",end=" + mEnd + "....." + mEntity.toString());
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                randomAccessFile.write(buffer, 0, len);
                progress += len;
                mEntity.setProgress_position(progress);
//                Logger.debug("main", "progress--------->" + (progress + lastFinshProgress));
            }
            mEntity.setProgress_position(mEntity.getProgress_position() + lastFinshProgress);
            randomAccessFile.close();
            mCallback.success(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Logger.debug("main", "save progress--------->" + mEntity.toString());
            DownloadHelper.getInstance().insert(mEntity);
        }

    }
}
