package csx.haha.com.optimization.s4;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import csx.haha.com.optimization.R;

/**
 * Created by shixing on 2018/3/21.
 */

public class S4BitmapActivity extends Activity {

    File mImageFile;
    File sdFileDir;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s4_bitmap);
        sdFileDir = Environment.getExternalStorageDirectory();
        mImageFile = new File(sdFileDir, "H.jpg");
    }


    /**
     * 2.尺寸压缩
     * 通过减少单位尺寸的像素值，真正意义的降低像素
     * 使用场景：
     *   缓存缩略图的时候
     *   头像
     * @param view
     */
    public void qualitCompressBySize(View view) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(mImageFile.getAbsolutePath(), options);
        compressImageToFileBySize(bitmap,new File(sdFileDir,"sizeCompress.jpeg"));
    }
    private void compressImageToFileBySize(Bitmap bitmap, File file) {
        //压缩尺寸倍数，值越大，图片的尺寸就越小
        int ratio = 4;
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth() / ratio,
                bitmap.getHeight() / ratio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        RectF rect = new RectF(0, 0, bitmap.getWidth() / ratio, bitmap.getHeight() / ratio);
        canvas.drawBitmap(bitmap, null, rect, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 质量压缩：
     * 原理：通过算法扣掉图片中的一些点附近的像素，达到降低质量来减少文件大小的目的
     * 注意：它其实只能实现对file的影响，但是对加载这个图片的bitmap内存是无法节省的，还是那么大
     * 因为bitmap在内存中的大小是按照像素计算的，也就是width*height,对于质量压缩，并不会改变图片的真是的像素
     *
     * 使用场景：
     *  1.将图片压缩后保存到本地
     *  2.将图片上传到服务器
     * @param view
     */
    public void qualitCompress(View view) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(mImageFile.getAbsolutePath(), options);
        compressImageToFile(bitmap,new File(sdFileDir,"qualityCompress.jpeg"));
    }

    private void compressImageToFile(Bitmap bitmap, File file) {
        //0 -> 100 (原图片的百分比，如100表示原图片的大小)
        int quality = 20;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
