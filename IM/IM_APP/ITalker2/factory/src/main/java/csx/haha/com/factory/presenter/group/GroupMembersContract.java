package csx.haha.com.factory.presenter.group;

import csx.haha.com.factory.model.db.view.MemberUserModel;
import csx.haha.com.factory.presenter.BaseContract;

/**
 * 群成员的契约
 */
public interface GroupMembersContract {
    interface Presenter extends BaseContract.IPresenter {
        // 具有一个刷新的方法
        void refresh();
    }

    // 界面
    interface View extends BaseContract.RecyclerView<Presenter, MemberUserModel> {
        // 获取群的ID
        String getGroupId();
    }
}
