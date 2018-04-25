package csx.haha.com.italker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import csx.haha.com.common.app.BaseActivity;
import csx.haha.com.italker.activities.MainActivity;
import csx.haha.com.italker.frags.assist.PermissionsFragment;

public class LaunchActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "LaunchActivity onResume: ");
        super.onResume();
        /**
         * android6.0以上的手机才会有运行时权限检查
         * 6.0一下的手机在安装时就被赋予权限，所以直接就可以进入MainActivity
         */
        if ( PermissionsFragment.haveAll(this, getSupportFragmentManager())) {
            MainActivity.show(this);
            finish();
        }
    }

}
