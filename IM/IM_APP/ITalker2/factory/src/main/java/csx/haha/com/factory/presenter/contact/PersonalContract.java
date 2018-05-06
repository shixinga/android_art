package csx.haha.com.factory.presenter.contact;

import csx.haha.com.factory.model.db.User;
import csx.haha.com.factory.presenter.BaseContract;

public interface PersonalContract {
    interface Presenter extends BaseContract.IPresenter {
        // 获取用户信息
        User getUserPersonal();
    }

    interface View extends BaseContract.IView<Presenter> {
        String getUserId();

        // 加载数据完成
        void onLoadDone(User user);

        // 是否发起聊天
        void allowSayHello(boolean isAllow);

        // 设置关注状态
        void setFollowStatus(boolean isFollow);
    }
}
