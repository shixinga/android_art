package one.com.nosharevalue;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by shixing on 2017/4/25.
 */

public class SecondActivity extends Activity {
    private TextView tv_second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv_second = (TextView) findViewById(R.id.tv_second);

        tv_second.setText("second id = " + MainActivity.id);
    }
}
