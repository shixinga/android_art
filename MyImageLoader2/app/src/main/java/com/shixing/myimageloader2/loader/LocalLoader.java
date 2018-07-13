package com.shixing.myimageloader2.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.shixing.myimageloader2.request.BitmapRequest;
import com.shixing.myimageloader2.utils.BitmapDecoder;
import com.shixing.myimageloader2.utils.ImageViewHelper;

import java.io.File;

/**
 * Created by shixing on 2018/7/11.
 */

public class LocalLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        final String path = Uri.parse(request.getImageUri()).getPath();
        File file = new File(path);
        if(!file.exists()){
            return null;
        }
        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path, options);
            }
        };

        return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()), ImageViewHelper.getImageViewHeight(request.getImageView()));

    }
}
