package com.shixing.filedownload.http;

import android.content.Context;

import com.shixing.filedownload.file.FileStorageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shixing on 2018/7/5.
 */

public class HttpManager {
    public static final int NETWORK_ERROR_CODE = 1;

    public static final int CONTENT_LENGTH_ERROR_CODE = 2;

    public static final int TASK_RUNNING_ERROR_CODE = 3;

    private static HttpManager sInstance = new HttpManager();
    private Context mContext;
    OkHttpClient mClient;
    private HttpManager() {
        mClient = new OkHttpClient();
    }
    public static HttpManager getInstance() {
        return sInstance;
    }
    public void init(Context context) {
        this.mContext = context;
    }

    /**
     * 同步请求
     * @param url
     * @return
     */
    public Response syncRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
             response = mClient.newCall(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 同步请求,断点续传
     * @param url
     * @param start 请求下载的文件的开始位置
     * @param end 请求下载的文件的结束位置
     * @return
     */
    public Response syncRequest(String url, long start, long end) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Range", "bytes=" + start + "-" + end)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    /**
     *
     * 只获取要下载的文件的长度
     *
     * @param url
     * @param callback
     */
    public void asyncRequestGetFileLength(final String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .head()
                .addHeader("Accept-Encoding", "identity")
                .build();
        mClient.newCall(request).enqueue(callback);
    }
    public void asyncRequest(final String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(callback);
    }

    public void asyncRequest(final String url, final DownloadCallback downloadCallback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() && downloadCallback != null) {
                    downloadCallback.fail(NETWORK_ERROR_CODE, "请求失败");
                    return;
                }
                File file = FileStorageManager.getInstance().getFileByName(url);
                FileOutputStream fos = new FileOutputStream(file);
                byte [] buffer = new byte[1024 * 50];
                int len = 0;
                InputStream is = response.body().byteStream();
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                downloadCallback.success(file);
            }
        });
    }
}
