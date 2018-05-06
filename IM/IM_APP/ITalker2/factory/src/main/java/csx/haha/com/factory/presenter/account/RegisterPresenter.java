package csx.haha.com.factory.presenter.account;

import android.text.TextUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

import csx.haha.com.common.Common;
import csx.haha.com.factory.data.DataSource;
import csx.haha.com.factory.data.helper.AccountHelper;
import csx.haha.com.factory.model.api.account.RegisterModel;
import csx.haha.com.factory.model.db.User;
import csx.haha.com.factory.persistence.Account;
import csx.haha.com.factory.presenter.BasePresenter;

/**
 * Created by sunray on 2018-4-26.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<User> {
    //子线程中执行
    @Override
    public void onDataLoaded(User user) {
        //当网络请求成功，注册好了，回送一个用户信息回来
        final RegisterContract.View iView = getView();
        if (iView == null) {
            return;
        }
        //在main线程中执行
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //调用主界面说注册成功
                iView.registerSuccess();
            }
        });
    }
    //子线程中执行
    @Override
    public void onDataNotAvailable(final String strRes) {
        //当网络请求注册失败，回送一个用户信息回来
        final RegisterContract.View iView = getView();
        if (iView == null) {
            return;
        }
        //在main线程中执行
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                //注册失败
                iView.showError(strRes);
            }
        });
    }

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String password) {
        //调用开始方法，在start中默认启动了loading
        start();

        //得到view接口
        RegisterContract.View view = getView();

        //校验
        if (!checkMobile(phone)) {
            view.showError("请输入正确的手机号！");
        } else if (name.length() < 2) {
            //称呼不能小于2位
            view.showError("称呼不能小于2位！");

        } else if (password.length() < 6) {
            //密码要大于6位
            view.showError("密码不能小于6位");
        } else {
            //进行网络请求
            //构造model，进行请求调用
            RegisterModel model = new RegisterModel(phone, name, password, Account.getPushId());
            //进行网络请求，并设置回送接口给自己
            AccountHelper.register(model, this);
        }
    }

    /**
     * 检查手机号是否合法
     * @param phone
     * @return
     */
    @Override
    public boolean checkMobile(String phone) {
        // 手机号不为空，并且满足格式
        return !TextUtils.isEmpty(phone)
                && Pattern.matches(Common.Constance.REGEX_MOBILE, phone);
    }

}
