package csx.haha.com.optimization.s4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import csx.haha.com.optimization.R;
import csx.haha.com.optimization.s3.S3WaitLockActivity;

/**
 * Created by sunray on 2018-3-17.
 */

public class S4Activity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s4);
    }

    public void click1(View view) {
        Intent intent = new Intent(this, S4BitmapActivity.class);
        startActivity(intent);
    }

}
