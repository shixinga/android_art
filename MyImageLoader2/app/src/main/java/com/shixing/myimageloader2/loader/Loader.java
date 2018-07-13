package com.shixing.myimageloader2.loader;

import com.shixing.myimageloader2.request.BitmapRequest;

/**
 * Created by shixing on 2018/7/11.
 */

public interface Loader {

    /**
     * 加载图片
     * @param request
     */
    void loadImage(BitmapRequest request);
}
