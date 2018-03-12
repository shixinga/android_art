package com.shixing.myimagewalllazyloading.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

/**
 * Created by shixing on 2017/11/24.
 */

public class ImageResizer {

    public static Bitmap decodeSampleBitmapFromFileDescriptor(FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
        options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor,null, options);
    }

    private static int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int intrinsicWidth = options.outWidth;
        int intrinsicHeight = options.outHeight;
        int halfIntrinsicWidth = intrinsicWidth / 2;
        int halfIntrinsicHeight = intrinsicHeight / 2;
        int inSampleSize = 1;
        while(halfIntrinsicWidth / inSampleSize >= reqWidth && halfIntrinsicHeight / inSampleSize >= reqHeight) {
            inSampleSize *= 2;
        }
        return inSampleSize;
    }
}
