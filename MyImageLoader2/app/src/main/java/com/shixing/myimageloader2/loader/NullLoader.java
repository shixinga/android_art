package com.shixing.myimageloader2.loader;

import android.graphics.Bitmap;
import android.util.Log;

import com.shixing.myimageloader2.request.BitmapRequest;

/**
 * Created by shixing on 2018/7/12.
 */

public class NullLoader extends AbstractLoader {
    private static final String TAG = "MainAcitivty";

    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        Log.d(TAG, "loadImage: 图片无法加载");
        return null;
    }
}
