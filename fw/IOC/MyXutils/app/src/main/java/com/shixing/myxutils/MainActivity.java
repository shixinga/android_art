package com.shixing.myxutils;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shixing.myxutils.annotation.ContentView;
import com.shixing.myxutils.annotation.OnClick;
import com.shixing.myxutils.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.tv)
    TextView tv;


    @Override
    public void init() {
        tv.setText("hahahh");
    }
    @OnClick(value = {R.id.bt1, R.id.bt2})
    public void click(View view) {
        Toast.makeText(this, "click:" + view.getId(), Toast.LENGTH_SHORT).show();
    }
}
