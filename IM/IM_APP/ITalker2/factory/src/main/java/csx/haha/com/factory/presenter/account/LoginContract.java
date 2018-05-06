package csx.haha.com.factory.presenter.account;

import android.support.annotation.StringRes;

import csx.haha.com.factory.presenter.BaseContract;

/**
 * Created by sunray on 2018-4-26.
 */

public interface LoginContract {
    interface View extends BaseContract.IView<Presenter> {
        //登录成功
        void loginSuccess();


    }

    interface Presenter extends BaseContract.IPresenter {
        //发起一个登录
        void login(String phone, String password);


    }
}
