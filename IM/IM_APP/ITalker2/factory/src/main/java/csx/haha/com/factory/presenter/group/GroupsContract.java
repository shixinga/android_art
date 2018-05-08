package csx.haha.com.factory.presenter.group;


import csx.haha.com.factory.model.db.Group;
import csx.haha.com.factory.presenter.BaseContract;

/**
 * 我的群列表契约
 *
 */
public interface GroupsContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.IPresenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, Group> {

    }
}
