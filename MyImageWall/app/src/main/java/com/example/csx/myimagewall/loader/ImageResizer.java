package com.example.csx.myimagewall.loader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

/**
 * Created by shixing on 2017/11/22.
 */

public class ImageResizer {


    public  static Bitmap decodeSampleBitmapFromFileDescripter(FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    public static Bitmap decodeSampleBitmapFromResource(Resources resources, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        int intrinsicWidth = options.outWidth;
        int intrinsicHeight = options.outHeight;
        int halfIntrinsicWidth= intrinsicWidth / 2;
        int halfIntrinsicHeight = intrinsicHeight / 2;
        while(halfIntrinsicHeight / inSampleSize >= reqHeight && halfIntrinsicWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2;
        }
        return inSampleSize;
    }
}
