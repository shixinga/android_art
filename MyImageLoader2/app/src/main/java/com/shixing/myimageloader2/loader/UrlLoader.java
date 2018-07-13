package com.shixing.myimageloader2.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.shixing.myimageloader2.request.BitmapRequest;
import com.shixing.myimageloader2.utils.BitmapDecoder;
import com.shixing.myimageloader2.utils.ImageViewHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shixing on 2018/7/11.
 */

public class UrlLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(final BitmapRequest request) {
        //先下载  后读取
        downloadImgByUrl(request.getImageUri(),getCache(request.getImageUriMD5()));
        BitmapDecoder decoder=new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(getCache(request.getImageUriMD5()).getAbsolutePath(),options);
            }
        };
        return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView())
                ,ImageViewHelper.getImageViewHeight(request.getImageView()));
    }

    public static boolean downloadImgByUrl(String urlStr, File file)
    {
        FileOutputStream fos = null;
        InputStream is = null;
        try
        {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {

                is = conn.getInputStream();
                fos = new FileOutputStream(file);
                byte[] buf = new byte[512];
                int len = 0;
                while ((len = is.read(buf)) != -1)
                {
                    fos.write(buf, 0, len);
                }
                fos.flush();
                return true;
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (is != null)
                    is.close();
            } catch (IOException e)
            {
            }

            try
            {
                if (fos != null)
                    fos.close();
            } catch (IOException e)
            {
            }
        }

        return false;

    }
    private File getCache(String unipue)
    {
        File file=new File(Environment.getExternalStorageDirectory(),"ImageLoader");
        if(!file.exists())
        {
            file.mkdir();
        }
        return new File(file,unipue);
    }

}
