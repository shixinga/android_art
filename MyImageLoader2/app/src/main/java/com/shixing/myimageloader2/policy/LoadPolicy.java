package com.shixing.myimageloader2.policy;

import com.shixing.myimageloader2.request.BitmapRequest;

/**
 * Created by shixing on 2018/7/11.
 */

public interface LoadPolicy {

    /**
     * 两个BitmapRequest进行比较
     * @param request1
     * @param request2
     * @return 小于0，request1 < request2，大于0，request1 > request2，等于
     */
    public int compareTo(BitmapRequest request1, BitmapRequest request2);
}
