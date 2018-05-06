package csx.haha.com.common.app;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import csx.haha.com.common.R;

/**
 * Created by sunray on 2018-4-29.
 */

public abstract class ToolbarActivity extends BaseActivity {
    protected Toolbar mToolbar;

    @Override
    protected void initWidget() {
        super.initWidget();
        //初始化toolbar
        initToolbar((Toolbar) findViewById(R.id.toolbar));
    }

    public void initToolbar(Toolbar toolbar) {
        mToolbar = toolbar;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        initTitleNeedBack();
    }

    private void initTitleNeedBack() {
        //设置左上角的返回按钮为实际的返回结果
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }


}
