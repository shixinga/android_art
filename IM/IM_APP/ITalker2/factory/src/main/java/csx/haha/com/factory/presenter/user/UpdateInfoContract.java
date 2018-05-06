package csx.haha.com.factory.presenter.user;


import csx.haha.com.factory.presenter.BaseContract;

/**
 * 跟新用户信息的基本约定
 * Created by sunray on 2018-4-28.
 */

public interface UpdateInfoContract {
    interface Presenter extends BaseContract.IPresenter {
        //更新
        void update(String photoFilePath, String desc, boolean isMan);
    }

    interface View extends BaseContract.IView<Presenter> {
        //回调成功
        void updateSucceed();
    }
}
