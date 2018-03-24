package com.shixing.mixture.fourth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shixing.mixture.R;

/**
 * Created by shixing on 2018/2/21.
 */

public class F4ListViewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f4_listview);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.bt_f4_flexlistview:
                Intent intent = new Intent(this, F4ListViewActivityFlexable.class);
                startActivity(intent);
                break;
            case R.id.bt_f4_refresh_listview:
                Intent intentSecond = new Intent(this, F4ListViewActivityRefreshListView.class);
                startActivity(intentSecond);
                break;
        }
    }
}
