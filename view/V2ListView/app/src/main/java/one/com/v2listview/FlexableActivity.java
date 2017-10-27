package one.com.v2listview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import static android.R.attr.data;

/**
 * Created by shixing on 2017/6/20.
 */

public class FlexableActivity extends Activity {

    private FlexableListview mLv_flexable;
    private String[] data = new String[30];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexable);
        for (int i = 0; i < 30; ++i) {
            data[i] = ".." + i;
        }
        mLv_flexable = (FlexableListview) findViewById(R.id.lv_flexable);
        mLv_flexable.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                data));
    }
}
