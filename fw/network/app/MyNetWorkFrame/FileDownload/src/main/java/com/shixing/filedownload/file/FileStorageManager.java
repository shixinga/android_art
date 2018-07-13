package com.shixing.filedownload.file;

import android.content.Context;
import android.os.Environment;

import com.shixing.filedownload.utils.Logger;
import com.shixing.filedownload.utils.Md5Uills;

import java.io.File;
import java.io.IOException;

/**
 * Created by shixing on 2018/7/3.
 */

public class FileStorageManager {

    private static FileStorageManager sInstance = new FileStorageManager();
    private Context mContext;
    private FileStorageManager() {
    }

    public static FileStorageManager getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public File getFileByName(String url) {
        File parent = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            parent = mContext.getExternalCacheDir();
        } else {
            parent = mContext.getCacheDir();
        }

        File file = new File(parent, Md5Uills.generateCode(url));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Logger.debug("main", "file exist");
        }

        return file;
    }
}
