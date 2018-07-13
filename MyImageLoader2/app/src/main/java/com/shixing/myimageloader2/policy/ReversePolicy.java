package com.shixing.myimageloader2.policy;

import com.shixing.myimageloader2.request.BitmapRequest;

/**
 * Created by shixing on 2018/7/11.
 */

public class ReversePolicy implements LoadPolicy {
    @Override
    public int compareTo(BitmapRequest request1, BitmapRequest request2) {
        return request2.getSerialNO() - request1.getSerialNO();
    }
}
