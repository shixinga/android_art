package one.com.transmit;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import one.com.transmit.model.Book;
import one.com.transmit.model.User;

public class MainActivity extends Activity {

    private TextView tvm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvm = (TextView) findViewById(R.id.tvm);
        tvm.setText(getCurProcessName(this));
    }


    public void secondOnClick(View view) {

        Intent intent = new Intent(this,SecondActivity.class);
        User user = new User(22,"csx",true);
        user.book = new Book();
        user.book.name  = "java";
        intent.putExtra("extra_user",user);
        startActivity(intent);
    }

    String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
}
