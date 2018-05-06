package csx.haha.com.factory.presenter.contact;

import csx.haha.com.factory.model.card.UserCard;
import csx.haha.com.factory.presenter.BaseContract;

/**
 * Created by sunray on 2018-5-1.
 */

public interface FollowContract {

    interface Presenter extends BaseContract.IPresenter {
        //关注某人
        void follow(String id);
    }

    interface View extends BaseContract.IView<Presenter> {
        //成功的情况下返回一个用户的信息
        void onFollowSucceed(UserCard userCard);
    }
}
