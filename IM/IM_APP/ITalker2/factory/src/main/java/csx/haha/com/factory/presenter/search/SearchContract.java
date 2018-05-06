package csx.haha.com.factory.presenter.search;

import java.util.List;

import csx.haha.com.factory.model.card.GroupCard;
import csx.haha.com.factory.model.card.UserCard;
import csx.haha.com.factory.presenter.BaseContract;

/**
 * Created by sunray on 2018-4-29.
 */

public interface SearchContract {
    interface Presenter extends BaseContract.IPresenter {
        //搜索内容
        void search(String content);
    }

    //搜索人的界面
    interface UserView extends BaseContract.IView<Presenter> {
        void onSearchDone(List<UserCard> userCards);
    }

    //搜索群的界面
    interface GroupView extends BaseContract.IView<Presenter> {
        void onSearchDone(List<GroupCard> groupCards);
    }
}
