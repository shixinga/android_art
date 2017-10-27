package com.shixing.a8motionevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
      /*  tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Toast.makeText(MainActivity.this,"ccc",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onTouch: setOnTouchListener");
                //true 意味着tv的dispathcTouchEvent()里面的super.dispathcTouchEvent()返回true，此时不会调用tv的onTouchEvent()
                //false意味着tv的dispathcTouchEvent()里面的super.dispathcTouchEvent()返回false，此时才会调用tv的onTouchEvent()
                return true;
            }
        });*/

       /*tv.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               Log.d(TAG, "onLongClick: !!!!!!!!!!!!!!!!!!!!!!!");
               return false;
           }
       });*/
        /*tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
            }
        });*/
    }

    //能被调用到Activity的onTouchEvent()，说明该Activity上的所有的view的onTouchEvent()都返回了false
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flagActivity = super.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent: flagActivity=" + flagActivity);
        return flagActivity;
    }
}
