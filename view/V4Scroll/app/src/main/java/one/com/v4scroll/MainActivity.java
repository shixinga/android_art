package one.com.v4scroll;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * 3大类滑动效果的实现方法：1。更改布局参数方式 2。scrollTo或scrollBy 3.动画
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void layoutParamsOnClick(View view) {
        Intent intent = new Intent(this,LayoutActivity.class);
        startActivity(intent);
    }

    public void scrollToAndscrollByOnClick(View view) {
        Intent intent = new Intent(this,ScrollActivity.class);
        startActivity(intent);
    }

    public void scrollerOnClick(View view) {
        Intent intent = new Intent(this,ScrollerActivity.class);
        startActivity(intent);
    }
}
