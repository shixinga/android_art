package com.shixing.myvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.shixing.myvolley.http.Volley;
import com.shixing.myvolley.http.interfaces.IDataListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public  static  final String url="http://192.168.1.205:8080/NetworkServer/LoginServlet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginClick(View view) {
        User user=new User();
        user.setName("13343491234");
        user.setPassword("123456");
        for (int i=0;i<50;i++)
        {
            Volley.sendRequest(user, url, LoginRespense.class, new IDataListener<LoginRespense>() {
                @Override
                public void onSuccess(LoginRespense loginRespense) {
                    Log.d(TAG,loginRespense.toString());
                }

                @Override
                public void onFail() {
                    Log.d(TAG,"获取失败");
                }
            });
        }



    }
}
