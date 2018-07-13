package com.shixing.filedownload.http;

import java.io.File;

public interface DownloadCallback {

    void success(File file);

    void fail(int errorCode, String errorMessage);

    void progress(int progress);
}
