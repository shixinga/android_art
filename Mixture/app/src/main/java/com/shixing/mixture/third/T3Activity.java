package com.shixing.mixture.third;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shixing.mixture.R;

/**
 * Created by shixing on 2018/2/20.
 */

public class T3Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t3);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.bt_t3_diff:
                Intent intent = new Intent(this, T3DifferentDirectionActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_t3_same:
                Intent intentSame = new Intent(this, T3SameDirectionActivity.class);
                startActivity(intentSame);
                break;
        }
    }
}
