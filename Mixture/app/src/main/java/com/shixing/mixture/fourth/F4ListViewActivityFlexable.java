package com.shixing.mixture.fourth;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.shixing.mixture.R;
import com.shixing.mixture.view.FlexableListView;

/**
 * Created by shixing on 2018/2/21.
 */

public class F4ListViewActivityFlexable extends Activity {
    private FlexableListView mLv_flexable;
    private String[] data = new String[30];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f4_listview_flexable);
        for (int i = 0; i < 30; ++i) {
            data[i] = ".." + i;
        }
        mLv_flexable = (FlexableListView) findViewById(R.id.lv_f4_flexable);
        mLv_flexable.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                data));
    }

}
