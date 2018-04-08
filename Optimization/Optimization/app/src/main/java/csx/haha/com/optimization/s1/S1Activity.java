package csx.haha.com.optimization.s1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import csx.haha.com.optimization.R;

/**
 * Created by sunray on 2018-3-12.
 */

public class S1Activity extends Activity {
    private static final String TAG = "MainActivity1";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "s1s1 onCreate: ");
        setContentView(R.layout.activity_s1);
    }

    public void click1(View view) {
        Intent intent = new Intent(this, S1LeakMemoryActivity.class);
        startActivity(intent);
    }
    public void click2(View view) {
        Intent intent = new Intent(this, S1MemoryChurnActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "s1s1 onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "s1s1 onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "s1s1 onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "s1s1 onDestroy: ");
    }
}
