package com.shixing.mynetworkframe;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.shixing.filedownload.db.DownloadEntity;
import com.shixing.filedownload.db.DownloadHelper;
import com.shixing.filedownload.file.FileStorageManager;
import com.shixing.filedownload.http.HttpManager;

import java.util.List;

/**
 * Created by shixing on 2018/7/3.
 */

public class MyApplication extends Application {
    private static final String TAG = "main";

    @Override
    public void onCreate() {
        super.onCreate();
        FileStorageManager.getInstance().init(this);
        HttpManager.getInstance().init(this);
//        Stetho.initializeWithDefaults(this);
        DownloadHelper.getInstance().init(this);
//        DownloadEntity entity = new DownloadEntity(1L,2L,3L,"http://www", 1, 1L);
//        DownloadHelper.getInstance().insert(entity);
//        List<DownloadEntity> list = DownloadHelper.getInstance().getAll("http://www");
//        Log.d(TAG, "onCreate:111 ");
//        for (DownloadEntity entity :list) {
//            Log.d(TAG, "onCreate: " + entity);
//        }
    }
}
