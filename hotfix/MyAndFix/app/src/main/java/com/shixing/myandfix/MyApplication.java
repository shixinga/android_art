package com.shixing.myandfix;

import android.app.Application;
import android.content.Context;

/**
 * Created by shixing on 2018/4/5.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initAndFix(this);

    }

    private void initAndFix(MyApplication myApplication) {
        AndFixManager manager = AndFixManager.getInstance();
        manager.init(myApplication);
    }
}
