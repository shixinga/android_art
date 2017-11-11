package com.shixing.q16setwifi;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shixing.q16setwifi.service.WifiManagerService;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    IWifiManager mIWifiManager;
    EditText mEt_name;
    EditText mEt_password;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            mIWifiManager = IWifiManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        mEt_name = (EditText) findViewById(R.id.et_name);
        mEt_password = (EditText) findViewById(R.id.et_password);

        Intent intent = new Intent(this, WifiManagerService.class);
        bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
    }

    public void setWifi(View view) {
        final String name = mEt_name.getText().toString();
        final String password = mEt_password.getText().toString();

        new Thread() {
            @Override
            public void run() {
                try {
                    int responseCodeOpenWifi = mIWifiManager.openWifi();
                    Log.d(TAG, "run: responseCodeOpenWifi=" + responseCodeOpenWifi);

                    if (responseCodeOpenWifi == 200) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"wifi打开成功", Toast.LENGTH_SHORT).show();
                            }
                        });

                        int responseCodeSetWifi = mIWifiManager.setWifiConfiguration(name, password);
                        Log.d(TAG, "run: responseCodeSetWifi=" + responseCodeSetWifi);

                        if (responseCodeSetWifi == 200) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"wifi设置成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"wifi打开失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
