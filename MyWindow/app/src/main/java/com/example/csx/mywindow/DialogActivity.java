package com.example.csx.mywindow;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/16.
 */

public class DialogActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        init();
    }

    private void init() {
        Dialog dialog = new Dialog(this);
//        Dialog dialog = new Dialog(getApplicationContext());
        TextView tv = new TextView(this);
        tv.setText("hi nihao ");
        dialog.setContentView(tv);
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR); //因为getApplicationContext()得到的context没有token，而token只有activity才体验，所以指定该对话框的window为系统的window
        dialog.show();
    }
}
