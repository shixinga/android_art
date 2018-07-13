package com.shixing.myimageloader2.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shixing on 2018/7/12.
 */

public class LoaderManager {
    private static LoaderManager mInstance = new LoaderManager();
    //加载器管理容器
    private Map<String, Loader> mLoaderMap = new HashMap<String, Loader>();

    private NullLoader mNullLoader = new NullLoader();

    private LoaderManager() {
        register("http", new UrlLoader());
        register("https", new UrlLoader());
        register("file", new LocalLoader());
    }

    /**
     * 根据图片地址的协议获取特定的图片加载器
     * @param schema
     * @return
     */
    public Loader getLoader(String schema){
        if(mLoaderMap.containsKey(schema)){
            return mLoaderMap.get(schema);
        }
        //没有找到合适的，返回空加载器
        return mNullLoader;
    }

    /**
     * 注册加载器，可以支持系统中未来出现的其他协议的加载器
     * @param schema
     * @param loader
     */
    private void register(String schema, Loader loader) {
        mLoaderMap.put(schema, loader);
    }



    public static LoaderManager getInstance(){
        return mInstance;
    }

}
