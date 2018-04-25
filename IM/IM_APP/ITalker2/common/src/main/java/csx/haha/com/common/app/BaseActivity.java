package csx.haha.com.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by sunray on 2018-4-17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();

        //初始化参数成功
        if (initArgs(getIntent().getExtras())) {
            int layId = getContentLayoutId();
            setContentView(layId);
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    /**
     * 初始化窗口
     */
    protected void initWindows() {
    }
    /**
     * 初始化相关参数
     * @param bundle
     * @return 初始化是否成功,成功返回true
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 得到当前Activity的layout id
     * @return 当前Activity的layout id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {
        ButterKnife.bind(this);
        Log.d(TAG, "initWidget: ");
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        //当点击界面导航导航返回时，finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //得到当前Activity下的所有Fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                //判断是否为我们能够处理的Fragment类型
                if(fragment instanceof BaseFragment) {
                    //判断fragment是否拦截了返回按钮
                    if (((BaseFragment)fragment).onBackPressed()) {
                        //如果fragment拦截（即交给该fragment处理返回事件）
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
