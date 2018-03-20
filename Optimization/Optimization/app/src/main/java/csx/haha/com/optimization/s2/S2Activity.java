package csx.haha.com.optimization.s2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import csx.haha.com.optimization.R;
import csx.haha.com.optimization.s1.S1MemoryChurnActivity;

/**
 * Created by sunray on 2018-3-12.
 */

public class S2Activity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s2);
    }

    public void click1(View view) {
        Intent intent = new Intent(this, S2ChatumLatinumActivity.class);
        startActivity(intent);
    }
    public void click2(View view) {
        Intent intent = new Intent(this, S2ChatumLatinumActivityOptimization.class);
        startActivity(intent);
    }

    public void click3(View view) {
        Intent intent = new Intent(this, S2DroidCardsActivity.class);
        startActivity(intent);
    }
    public void click4(View view) {
        Intent intent = new Intent(this, S2DroidCardsActivityOptimization.class);
        startActivity(intent);
    }
}
