package com.shixing.mixture.second;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shixing.mixture.R;

/**
 * Created by shixing on 2018/2/17.
 */

public class S2Activity extends Activity {
    public static int sUid = 1;
    private TextView mTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s2);
        mTv = findViewById(R.id.tv_s2_a);
        mTv.setText("sUid= " + sUid);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.s2_secondActivity:
                Intent intent = new Intent(this, S2BActivity.class);
                sUid = 2;
                Toast.makeText(this, "sUid被修改为：" + sUid, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
        }
    }
}
