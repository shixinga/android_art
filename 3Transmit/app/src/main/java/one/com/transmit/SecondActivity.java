package one.com.transmit;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import one.com.transmit.model.User;

/**
 * Created by shixing on 2017/4/26.
 */

public class SecondActivity extends Activity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv = (TextView) findViewById(R.id.tv);
        //Serializable或者Parcelable都可以传递进程间数据
//        User user = (User) getIntent().getSerializableExtra("extra_user");
        User user = (User) getIntent().getParcelableExtra("extra_user");
        tv.setText(user.userId + ".." + user.userName + ".." + user.isMale + "..bookname=" + user.book.name
            + getCurProcessName(this));
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
