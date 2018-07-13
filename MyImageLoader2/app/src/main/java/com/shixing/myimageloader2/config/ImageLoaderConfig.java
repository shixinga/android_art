package com.shixing.myimageloader2.config;

import com.shixing.myimageloader2.cache.BitmapCache;
import com.shixing.myimageloader2.cache.NoCache;
import com.shixing.myimageloader2.policy.LoadPolicy;
import com.shixing.myimageloader2.policy.SerialPolicy;

/**
 * Created by shixing on 2018/7/11.
 */

public class ImageLoaderConfig {

    //缓存策略
    private BitmapCache bitmapCache = new NoCache();
    //加载策略
    private LoadPolicy loadPolicy = new SerialPolicy();
    //线程个数
    private int threadCount = Runtime.getRuntime().availableProcessors();
    //图片加载的显示配置
    private DisplayConfig displayConfig = new DisplayConfig();

    private ImageLoaderConfig(){
    }

    //生成器模式（不同的构建过程，生成不同表现形式的对象）
    //AlertDialog
    public static class Builder{
        private ImageLoaderConfig config;

        public Builder() {
            config = new ImageLoaderConfig();
        }
        /**
         * 设置缓存策略
         * @param bitmapCache
         * @return
         */
        public Builder setCachePolicy(BitmapCache bitmapCache){
            config.bitmapCache = bitmapCache;
            return this;
        }

        /**
         * 设置加载策略
         * @param loadPolicy
         * @return
         */
        public Builder setLoadPolicy(LoadPolicy loadPolicy){
            config.loadPolicy = loadPolicy;
            return this;
        }

        /**
         * 设置线程个数
         * @param count
         * @return
         */
        public Builder setThreadCount(int count){
            config.threadCount = count;
            return this;
        }
        /**
         * 图片加载过程中显示的图片
         * @param resId
         * @return
         */
        public Builder setLoadingPlaceHolder(int resId){
            config.displayConfig.loadingImg = resId;
            return this;
        }

        /**
         * 图片加载失败显示的图片
         * @param resId
         * @return
         */
        public Builder setFailedPlaceHolder(int resId){
            config.displayConfig.failedImg = resId;
            return this;
        }

        public ImageLoaderConfig build(){
            return config;
        }
    }

    public int getThreadCount() {
        return threadCount;
    }

    public LoadPolicy getLoadPolicy() {
        return loadPolicy;
    }

    public BitmapCache getBitmapCache() {
        return bitmapCache;
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

}
