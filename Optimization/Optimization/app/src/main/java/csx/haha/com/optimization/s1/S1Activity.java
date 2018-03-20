package csx.haha.com.optimization.s1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import csx.haha.com.optimization.R;

/**
 * Created by sunray on 2018-3-12.
 */

public class S1Activity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
