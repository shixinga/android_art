package csx.haha.com.common.app;

import android.content.Context;
import android.support.annotation.StringRes;

import csx.haha.com.factory.presenter.BaseContract;

/**
 * Created by sunray on 2018-4-26.
 */

public abstract class PresenterFragment<Presenter extends BaseContract.IPresenter> extends BaseFragment
        implements BaseContract.IView<Presenter> {
    protected Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //在界面onAttach之后就触发初始化Presenter
        initPresenter();
    }

    protected abstract void initPresenter();


    @Override
    public void showError( String str) {
        if (mPlaceHolderView != null) {
            mPlaceHolderView.triggerError(str);
        } else {
            MyApplication.showToast(str);
        }
    }

    @Override
    public void showLoading() {
        if (mPlaceHolderView != null) {
            mPlaceHolderView.triggerLoading();
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.destroy();
    }
}
