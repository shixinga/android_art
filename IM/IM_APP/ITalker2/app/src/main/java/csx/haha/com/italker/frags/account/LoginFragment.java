package csx.haha.com.italker.frags.account;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;
import csx.haha.com.common.app.BaseFragment;
import csx.haha.com.common.app.PresenterFragment;
import csx.haha.com.factory.presenter.account.LoginContract;
import csx.haha.com.factory.presenter.account.LoginPresenter;
import csx.haha.com.italker.R;
import csx.haha.com.italker.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends PresenterFragment<LoginContract.Presenter>
        implements LoginContract.View {
    private static final String TAG = "MainActivity";
    private AccountTrigger mAccountTrigger;

    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_password)
    EditText mPassword;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//Log.d(TAG, "LoginFragment onAttach: ");
        //得到AccountActivity的引用
        mAccountTrigger = (AccountTrigger) context;

    }

    @Override
    protected void initPresenter() {
        new LoginPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String phone = mPhone.getText().toString();
        String password = mPassword.getText().toString();
        //调用Presenter层进行注册
        mPresenter.login(phone, password);

    }

    @OnClick(R.id.txt_go_register)
    void onShowLoginClick() {
        //让AccountActivity进行界面切换
        mAccountTrigger.triggerView();
    }

    @Override
    public void onResume() {
//Log.d(TAG, "LoginFragment onResume: ");
        super.onResume();
        //默认切换为注册界面
//        mAccountTrigger.triggerView();
    }

    @Override
    public void showError(String str) {
        super.showError(str);
        //走到这里意味着注册失败

        //停止loading
        mLoading.stop();
        //让控件可以输入
        mPhone.setEnabled(true);
        mPassword.setEnabled(true);
        //提交按钮可以继续点击
        mSubmit.setEnabled(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        //停止loading
        mLoading.start();
        //让控件不可以输入
        mPhone.setEnabled(false);
        mPassword.setEnabled(false);
        //提交按钮不可以继续点击
        mSubmit.setEnabled(false);
    }

    @Override
    public void loginSuccess() {
        //登录成功后跳到MainActivity
        MainActivity.show(getContext());
        getActivity().finish();
    }
}
