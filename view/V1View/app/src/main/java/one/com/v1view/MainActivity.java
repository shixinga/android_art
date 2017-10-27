package one.com.v1view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private Intent mIntent;
    //下面的代码会报nullPoiterException，因为MainActivity对象被创建时，就会调用new Intent(this,SecondActivity.class)
    //而new Intent（）中，需要用到该MainActivity对象所attach的ContextImpl对象，该MainActivity对象所attach的ContextImpl对象是在
    //MainActivity对象被创建后才会被MainActivity对象所attach()【在ActivityThread.java中的performLaunchActivity()中被attach】
    //解决方法：1.在onCreate()中调用mIntent  = new Intent(this,SecondActivity.class);
    //private Intent mIntent= new Intent(this,SecondActivity.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIntent  = new Intent(this,SecondActivity.class);
    }

     public void measureViewOnClick(View view) {
        mIntent.putExtra("flag",1);
        startActivity(mIntent);
    }
    public void topBarOnClick(View view) {
        mIntent.putExtra("flag",2);
        startActivity(mIntent);
    }
    public void myTextviewOnClick(View view) {
        mIntent.putExtra("flag",3);
        startActivity(mIntent);
    }
    public void shineTextviewOnClick(View view) {
        mIntent.putExtra("flag",4);
        startActivity(mIntent);
    }
    public void circleProcessOnClick(View view) {
        mIntent.putExtra("flag",5);
        startActivity(mIntent);
    }
    public void volumeViewOnClick(View view) {
        mIntent.putExtra("flag",6);
        startActivity(mIntent);
    }
    public void myScrollviewOnClick(View view) {
        mIntent.putExtra("flag",7);
        startActivity(mIntent);
    }

    public void dispatchViewOnClick(View view) {
        mIntent.putExtra("flag",8);
        startActivity(mIntent);
    }
}
