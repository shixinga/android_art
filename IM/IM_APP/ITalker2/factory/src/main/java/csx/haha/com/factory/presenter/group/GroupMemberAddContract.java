package csx.haha.com.factory.presenter.group;


import csx.haha.com.factory.presenter.BaseContract;

/**
 * 群成员添加的契约
 *
 */
public interface GroupMemberAddContract {
    interface Presenter extends BaseContract.IPresenter {
        // 提交成员
        void submit();

        // 更改一个Model的选中状态
        void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected);
    }

    // 界面
    interface View extends BaseContract.RecyclerView<Presenter, GroupCreateContract.ViewModel> {
        // 添加群成员成功
        void onAddedSucceed();

        // 获取群的Id
        String getGroupId();
    }
}
