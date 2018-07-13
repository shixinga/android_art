package com.shixing.myimageloader2.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.shixing.myimageloader2.config.DisplayConfig;
import com.shixing.myimageloader2.core.SimpleImageLoader;
import com.shixing.myimageloader2.policy.LoadPolicy;
import com.shixing.myimageloader2.utils.MD5Utils;

import java.lang.ref.SoftReference;

/**
 * Created by shixing on 2018/7/11.
 */

public class BitmapRequest implements Comparable<BitmapRequest> {
    //加载策略
    private LoadPolicy loadPolicy = SimpleImageLoader.getObject().getConfig().getLoadPolicy();
    //序列号
    private int serialNO;
    //图片控件
    //当系统内存不足时，把引用的对象进行回收
    private SoftReference<ImageView> mImageViewRef;
    //图片路径
    private String imageUri;
    //MD5的图片路径
    private String imageUriMD5;

    private DisplayConfig displayConfig = SimpleImageLoader.getObject().getConfig().getDisplayConfig();
    public SimpleImageLoader.ImageListener imageListener;

    public BitmapRequest(ImageView imageView, String uri, DisplayConfig config, SimpleImageLoader.ImageListener imageListener) {
        this.mImageViewRef = new SoftReference<ImageView>(imageView);
        //设置可见的ImageView的tag为，要下载的图片路径
        imageView.setTag(uri);
        this.imageUri = uri;
        this.imageUriMD5 = MD5Utils.toMD5(imageUri);
        if(config != null){
            this.displayConfig = config;
        }
        this.imageListener = imageListener;
    }

    @Override
    public int compareTo(@NonNull BitmapRequest o) {
        return loadPolicy.compareTo(this, o);
    }

    /**
     * 设置序列号
     * @param serialNO
     */
    public void setSerialNO(int serialNO) {
        this.serialNO = serialNO;
    }

    public int getSerialNO() {
        return serialNO;
    }

    public ImageView getImageView() {
        return mImageViewRef.get();
    }

    public String getImageUri() {
        return imageUri;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((loadPolicy == null) ? 0 : loadPolicy.hashCode());
        result = prime * result + serialNO;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BitmapRequest other = (BitmapRequest) obj;
        if (loadPolicy == null) {
            if (other.loadPolicy != null)
                return false;
        } else if (!loadPolicy.equals(other.loadPolicy))
            return false;
        if (serialNO != other.serialNO)
            return false;
        return true;
    }
    public String getImageUriMD5() {
        return imageUriMD5;
    }

}
