package com.shixing.l01memory.second;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by shixing on 2018/3/11.
 */

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView view = new MyView(this);
        setContentView(view);

    }
}
