package csx.haha.com.factory.presenter.account;

import android.support.annotation.StringRes;

import csx.haha.com.factory.presenter.BaseContract;

/**
 * Created by sunray on 2018-4-26.
 */

public interface RegisterContract {
    interface View extends BaseContract.IView<Presenter> {
        //注册成功
        void registerSuccess();


    }

    interface Presenter extends BaseContract.IPresenter {
        //发起一个注册
        void register(String phone, String name, String password);

        //检查手机号是否正确
        boolean checkMobile(String phone);


    }
}
