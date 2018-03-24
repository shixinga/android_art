package com.shixing.mixture.fourth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.shixing.mixture.R;
import com.shixing.mixture.second.S2Activity;
import com.shixing.mixture.third.T3Activity;

import java.io.ObjectOutputStream;

/**
 * Created by shixing on 2018/2/21.
 */

public class F4Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f4);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.bt_f4_selfview:
                Intent intent = new Intent(this, F4SelfViewActivity.class);
                intent.setAction("com.csx.fitst");
                startActivity(intent);
                break;
            case R.id.bt_f4_combineview_animator:
                Intent intentSecond = new Intent(this, F4CombineAnimatorActivity.class);
                startActivity(intentSecond);
                break;
            case R.id.bt_f4_combineview_animation:
                Intent intentThird = new Intent(this, F4CombineAnimationActivity.class);
                startActivity(intentThird);
                break;
            case R.id.bt_f4_slidemenu:
                Intent intentFourth = new Intent(this, F4SlideViewGroupAvtivity.class);
                startActivity(intentFourth);
                break;
            case R.id.bt_f4_listview:
                Intent intentFifth = new Intent(this, F4ListViewActivity.class);
                startActivity(intentFifth);
                break;
        }
    }
}
