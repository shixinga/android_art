package com.shixing.filedownload;

import com.shixing.filedownload.http.DownloadCallback;

/**
 * Created by shixing on 2018/7/8.
 */

public class DownloadTask {
    private String mUrl;

    private DownloadCallback mCallback;

    public DownloadTask(String mUrl, DownloadCallback mCallback) {
        this.mUrl = mUrl;
        this.mCallback = mCallback;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public DownloadCallback getCallback() {
        return mCallback;
    }

    public void setCallback(DownloadCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloadTask that = (DownloadTask) o;

        if (mUrl != null ? !mUrl.equals(that.mUrl) : that.mUrl != null) return false;
        return mCallback != null ? mCallback.equals(that.mCallback) : that.mCallback == null;

    }

    @Override
    public int hashCode() {
        int result = mUrl != null ? mUrl.hashCode() : 0;
        result = 31 * result + (mCallback != null ? mCallback.hashCode() : 0);
        return result;
    }
}
