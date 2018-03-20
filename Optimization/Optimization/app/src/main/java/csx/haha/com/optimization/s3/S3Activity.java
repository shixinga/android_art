package csx.haha.com.optimization.s3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import csx.haha.com.optimization.R;
import csx.haha.com.optimization.s2.S2ChatumLatinumActivity;
import csx.haha.com.optimization.s2.S2ChatumLatinumActivityOptimization;
import csx.haha.com.optimization.s2.S2DroidCardsActivity;
import csx.haha.com.optimization.s2.S2DroidCardsActivityOptimization;

/**
 * Created by sunray on 2018-3-17.
 */

public class S3Activity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s3);
    }

    public void click1(View view) {
        Intent intent = new Intent(this, S3WaitLockActivity.class);
        startActivity(intent);
    }
    public void click2(View view) {
        Intent intent = new Intent(this, S3AlarmManagerActivity.class);
        startActivity(intent);
    }

    public void click3(View view) {
        Intent intent = new Intent(this, S3JobServiceActivity.class);
        startActivity(intent);
    }

}
