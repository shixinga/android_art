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

public class F1Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f1);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, "onNewIntent() was invoked!", Toast.LENGTH_SHORT).show();
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.standard:
                Intent intent = new Intent(this,F1Activity.class);
                startActivity(intent);
                break;
            case R.id.singleTop:
                Intent intentTop = new Intent(this,F1BActivity.class);
                startActivity(intentTop);
                break;
            case R.id.singleTask:
                Intent intentTask = new Intent(this, F1CActivity.class);
                startActivity(intentTask);
                break;
            case R.id.singleInstance:
                Intent intentInstance = new Intent(this, F1DActivity.class);
                startActivity(intentInstance);
                break;
        }
    }
}
