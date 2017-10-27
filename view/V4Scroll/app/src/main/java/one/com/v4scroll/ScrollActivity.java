package one.com.v4scroll;

import android.app.Activity;
import android.os.Bundle;

/**
 * 包含三种实现滑动效果的方法，都在自定义的MyViewCanMove的onTouchEvent()里面
 * Created by shixing on 2017/6/21.
 */
public class ScrollActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
    }
}
