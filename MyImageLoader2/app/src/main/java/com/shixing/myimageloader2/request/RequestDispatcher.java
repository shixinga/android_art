package com.shixing.myimageloader2.request;

import android.util.Log;

import com.shixing.myimageloader2.loader.Loader;
import com.shixing.myimageloader2.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * Created by shixing on 2018/7/11.
 */

public class RequestDispatcher extends Thread {
    private static final String TAG = "MainActivity";
    //请求队列
    private BlockingQueue<BitmapRequest> mRequestQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    @Override
    public void run() {
        //非阻塞状态，获取请求处理
        while (!isInterrupted()) {
            //从队列中获取优先级最高的请求进行处理
            try {
                BitmapRequest request = mRequestQueue.take();
                Log.d(TAG, "run: 处理请求" + request.getSerialNO());
                //解析图片地址，获取对象的加载器
                String schema = parseSchema(request.getImageUri());
                Loader loader = LoaderManager.getInstance().getLoader(schema);
                loader.loadImage(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 解析图片地址，获取schema
     * @param imageUri
     * @return
     */
    private String parseSchema(String imageUri) {
        if(imageUri.contains("://")){
            return imageUri.split("://")[0];
        }else{
            Log.d("MainActivity", "图片地址schema异常！");
        }
        return null;
    }
}
