package csx.haha.com.optimization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import csx.haha.com.optimization.s1.S1Activity;
import csx.haha.com.optimization.s2.S2Activity;
import csx.haha.com.optimization.s3.S3Activity;
import csx.haha.com.optimization.s4.S4Activity;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View view) {
        Intent intent = new Intent(this,S1Activity.class);
        startActivity(intent);
    }
    public void click2(View view) {
        Intent intent = new Intent(this,S2Activity.class);
        startActivity(intent);
    }
    public void click3(View view) {
        Intent intent = new Intent(this,S3Activity.class);
        startActivity(intent);
    }
    public void click4(View view) {
        Intent intent = new Intent(this,S4Activity.class);
        startActivity(intent);
    }
}
