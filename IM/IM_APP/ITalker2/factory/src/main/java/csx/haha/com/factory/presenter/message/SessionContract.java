package csx.haha.com.factory.presenter.message;

import csx.haha.com.factory.model.db.Session;
import csx.haha.com.factory.presenter.BaseContract;

public interface SessionContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.IPresenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, Session> {

    }
}