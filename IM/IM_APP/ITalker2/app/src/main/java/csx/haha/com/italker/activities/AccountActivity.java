package csx.haha.com.italker.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;

import csx.haha.com.common.app.BaseActivity;
import csx.haha.com.italker.R;
import csx.haha.com.italker.frags.account.UpdateInfoFragment;
import csx.haha.com.italker.frags.main.ContactFragment;

public class AccountActivity extends BaseActivity {
    private Fragment mCurFragment;

    /**
     * 账户Activity显示的入口
     * @param context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mCurFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCurFragment.onActivityResult(requestCode,resultCode,data);
    }
}
