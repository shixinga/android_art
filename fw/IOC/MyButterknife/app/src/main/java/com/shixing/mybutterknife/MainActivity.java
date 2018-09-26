package com.shixing.mybutterknife;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inject.InjectView;
import com.shixing.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectView.bind(this);
//        new BindViewa().bindView(this);
        Toast.makeText(this, "--->" + textView, Toast.LENGTH_SHORT).show();
    }

//    class BindViewa {
//        public void bindView(Activity activity) {
//            textView = activity.findViewById(R.id.tv);
//        }
//    }
}
