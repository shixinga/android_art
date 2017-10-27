package one.com.v1view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by shixing on 2017/6/19.
 */

public class SecondActivity extends Activity {
    public static final String TAG = "SecondActivity";
    private MyTopBar myTopbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flag = getIntent().getIntExtra("flag", -1);
        Log.d(TAG, "onCreate: flag=" + flag);
        switch (flag) {
            case 1:
                setContentView(R.layout.activity_view_measure);
                break;
            case 2:;
                setContentView(R.layout.activity_view_topbar);
                 myTopbar = (MyTopBar) findViewById(R.id.myTopbar);
                myTopbar.setmIbuttoneClickListener(new MyTopBar.IButtonClickListener() {
                    @Override
                    public void leftButtonClick() {
                        Toast.makeText(SecondActivity.this,"left",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void rightButtonClick() {
                        Toast.makeText(SecondActivity.this,"right!",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 3:;
            case 4:;
            case 8:
                setContentView(R.layout.activity_view_dispatch);
                break;
        }
    }
}
