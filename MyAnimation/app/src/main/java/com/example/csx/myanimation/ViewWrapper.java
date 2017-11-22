package com.example.csx.myanimation;

import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/11/15.
 */

public class ViewWrapper {
    View mTarget;

    public ViewWrapper(View mTarget) {
        this.mTarget = mTarget;
    }

   public int getWidth() {
       Log.d(AnimatorActivity.TAG, "getWidth: " + mTarget.getLayoutParams().width + " getWidth()=" + mTarget.getWidth());
       return mTarget.getWidth();
   }

   public void setWidth(int width) {
       mTarget.getLayoutParams().width = width;
       mTarget.requestLayout();
   }
}
