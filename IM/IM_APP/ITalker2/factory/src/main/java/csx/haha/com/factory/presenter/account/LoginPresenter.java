package csx.haha.com.factory.presenter.account;

import android.text.TextUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import csx.haha.com.factory.data.DataSource;
import csx.haha.com.factory.data.helper.AccountHelper;
import csx.haha.com.factory.model.api.account.LoginModel;
import csx.haha.com.factory.model.db.User;
import csx.haha.com.factory.persistence.Account;
import csx.haha.com.factory.presenter.BasePresenter;

/**
 * 登录的逻辑实现
 * Created by sunray on 2018-4-27.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter, DataSource.Callback<User> {
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {
        start();

        final LoginContract.View view = getView();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            view.showError("参数为空");
        } else {
            //尝试传递pushId
            LoginModel model = new LoginModel(phone, password, Account.getPushId());
            AccountHelper.login(model, this);
        }
    }

    //子线程中执行
    @Override
    public void onDataLoaded(User user) {
        final LoginContract.View view = getView();
        if (view == null) {
            return;
        }
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.loginSuccess();
            }
        });
    }

    //子线程中运行
    @Override
    public void onDataNotAvailable(final String strRes) {
        final LoginContract.View view = getView();
        if (view == null) {
            return;
        }
        Run.onUiAsync(new Action() {
            @Override
            public void call() {

                view.showError(strRes);
            }
        });
    }
}
