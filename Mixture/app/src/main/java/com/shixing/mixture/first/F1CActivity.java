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

public class F1CActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f1c);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, "C onNewIntent() was invoked!", Toast.LENGTH_SHORT).show();
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.singleTop_b_fromc:
                Intent intent = new Intent(this, F1BActivity.class);
                startActivity(intent);
                break;
        }
    }
}
