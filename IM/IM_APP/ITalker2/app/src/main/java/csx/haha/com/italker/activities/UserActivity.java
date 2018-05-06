package csx.haha.com.italker.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import butterknife.BindView;
import csx.haha.com.common.app.BaseActivity;
import csx.haha.com.italker.R;
import csx.haha.com.italker.frags.user.UpdateInfoFragment;

public class UserActivity extends BaseActivity {
    private Fragment mCurFragment;
    @BindView(R.id.im_bg)
    ImageView mBg;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_user;
    }

    /**
     * 账户Activity显示的入口
     * @param context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, UserActivity.class));
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
