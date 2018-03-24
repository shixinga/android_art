package com.shixing.mixture.first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.shixing.mixture.R;

/**
 * Created by shixing on 2018/2/17.
 */

public class F1BActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f1b);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, "onNewIntent() was invoked!", Toast.LENGTH_SHORT).show();
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.singleTop_b:
                Intent intent = new Intent(this, F1BActivity.class);
                startActivity(intent);
                break;
            case R.id.singleTask_fromb:
                Intent intentC = new Intent(this,F1CActivity.class);
                startActivity(intentC);
        }
    }
}
