package csx.haha.com.italker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import csx.haha.com.common.Common;
import csx.haha.com.common.app.BaseActivity;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTvTest.setText("caoo f");

    }
}
