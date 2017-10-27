package com.shixing.a8switchbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    ToggleView mToggleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mToggleView = (ToggleView) findViewById(R.id.toggleView);
//        mToggleView.setmBitmapSlideView(R.drawable.slide_button);
//        mToggleView.setmBitmapToggleBackground(R.drawable.switch_background);
        mToggleView.setmOnStateChangedListener(new ToggleView.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {
                Toast.makeText(MainActivity.this,"更新状态为：" + state,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
