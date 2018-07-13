package com.shixing.myimageloader2.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.shixing.myimageloader2.request.BitmapRequest;

/**
 * Created by shixing on 2018/7/11.
 */

public class MemoryCache implements BitmapCache {
    private static final String TAG = "MainActivity";
    private LruCache<String, Bitmap> mLruCache;

    public MemoryCache() {
        //缓存的最大值（可用内存的1/8）
        int maxSize = (int) (Runtime.getRuntime().freeMemory() / 1024 / 8);
        this.mLruCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //一个bitmap的大小
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        Log.d(TAG, "put: put in memory cache : " + request.getImageUri() );
        mLruCache.put(request.getImageUriMD5(), bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        Log.d(TAG, "put: get in memory cache : " + request.getImageUri());
        return mLruCache.get(request.getImageUriMD5());
    }

    @Override
    public void remove(BitmapRequest request) {
        mLruCache.remove(request.getImageUriMD5());
    }
}
