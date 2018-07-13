package com.shixing.myimageloader2.core;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.shixing.myimageloader2.config.DisplayConfig;
import com.shixing.myimageloader2.config.ImageLoaderConfig;
import com.shixing.myimageloader2.request.BitmapRequest;
import com.shixing.myimageloader2.request.RequestQueue;

/**
 * Created by shixing on 2018/7/11.
 */

public class SimpleImageLoader {

    private ImageLoaderConfig mConfig;

    //网络请求队列
    private RequestQueue mRequestQueue;

    private static SimpleImageLoader mInstance;

    private SimpleImageLoader() {
    }
    private SimpleImageLoader(ImageLoaderConfig config) {
        this.mConfig = config;
        //初始化请求队列
        mRequestQueue = new RequestQueue(config.getThreadCount());
        //开始，请求转发线程开始不断从队列中获取请求，进行转发处理
        mRequestQueue.start();
    }
    /**
     * 获取SimpleImageLoader的实例
     * @return 只有在SimpleImageLoader getInstance(ImageLoaderConfig config)调用过之后，才能返回一个实例化了的SimpleImageLoader对象
     */
    public static SimpleImageLoader getInstance(ImageLoaderConfig config) {
        if (mInstance == null) {
            synchronized (SimpleImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new SimpleImageLoader(config);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取SimpleImageLoader的实例
     * @return 只有在SimpleImageLoader getInstance(ImageLoaderConfig config)调用过之后，才能返回一个实例化了的SimpleImageLoader对象
     */
    public static SimpleImageLoader getObject(){
        if(mInstance == null){
            throw new UnsupportedOperationException("getInstance(ImageLoaderConfig config) 没有执行过！");
        }
        return mInstance;
    }

    public void displayImage(ImageView imageView,String uri){
        displayImage(imageView, uri, null, null);
    }
    /**
     * 显示图片
     * @param imageView 要显示图片的控件
     * @param uri 图片路径
     * @param config 显示配置
     * @param imageListener 图片加载的监听
     */
    public void displayImage(ImageView imageView, String uri,
                             DisplayConfig config, ImageListener imageListener){
        //生成一个请求，添加到请求队列中
        BitmapRequest request = new BitmapRequest(imageView, uri, config, imageListener);
        mRequestQueue.addRequest(request);

    }
    /**
     * 图片加载的监听
     */
    public static interface ImageListener{
        /**
         * 加载完成
         * @param imageView
         * @param bitmap
         * @param uri
         */
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }

    public ImageLoaderConfig getConfig() {
        return mConfig;
    }
}
