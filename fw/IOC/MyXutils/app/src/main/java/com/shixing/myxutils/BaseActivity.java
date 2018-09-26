package com.shixing.myxutils;


import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectLayout(this);
        InjectUtils.injectView(this);
        InjectUtils.injectClickView(this);
        init();
    }
    public abstract void init();
}
