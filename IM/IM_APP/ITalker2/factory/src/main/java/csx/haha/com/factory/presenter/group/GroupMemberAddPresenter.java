package csx.haha.com.factory.presenter.group;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import csx.haha.com.factory.Factory;
import csx.haha.com.factory.R;
import csx.haha.com.factory.data.DataSource;
import csx.haha.com.factory.data.helper.GroupHelper;
import csx.haha.com.factory.data.helper.UserHelper;
import csx.haha.com.factory.model.api.group.GroupMemberAddModel;
import csx.haha.com.factory.model.card.GroupMemberCard;
import csx.haha.com.factory.model.db.view.MemberUserModel;
import csx.haha.com.factory.model.db.view.UserSampleModel;
import csx.haha.com.factory.presenter.BaseRecyclerPresenter;

/**
 * 群成员添加的逻辑
 *
 */
public class GroupMemberAddPresenter extends BaseRecyclerPresenter<GroupCreateContract.ViewModel,
        GroupMemberAddContract.View> implements GroupMemberAddContract.Presenter,
        DataSource.Callback<List<GroupMemberCard>> {
    private Set<String> users = new HashSet<>();

    public GroupMemberAddPresenter(GroupMemberAddContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();

        // 加载可以添加的用户
        Factory.runOnAsync(loader);
    }

    @Override
    public void submit() {
        GroupMemberAddContract.View view = getView();
        view.showLoading();

        // 判断参数
        if (users.size() == 0) {
            view.showError("无法添加成员");
            return;
        }

        // 进行网络请求
        GroupMemberAddModel model = new GroupMemberAddModel(users);
        GroupHelper.addMembers(view.getGroupId(), model, this);
    }

    @Override
    public void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected) {
        if (isSelected)
            users.add(model.author.getId());
        else
            users.remove(model.author.getId());
    }

    private Runnable loader = new Runnable() {
        @Override
        public void run() {
            GroupMemberAddContract.View view = getView();
            if (view == null)
                return;

            // 我所有的联系人
            List<UserSampleModel> contact = UserHelper.getSampleContact();
            // 以及在群里的人
            List<MemberUserModel> members = GroupHelper.getMemberUsers(view.getGroupId(), -1);

            for (MemberUserModel member : members) {
                // 如果有就进行移除
                int index = indexOfUserContact(contact, member.userId);
                if (index >= 0)
                    contact.remove(index);
            }

            // 返回一个界面显示的Model
            List<GroupCreateContract.ViewModel> models = new ArrayList<>();
            for (UserSampleModel model : contact) {
                GroupCreateContract.ViewModel viewModel = new GroupCreateContract.ViewModel();
                viewModel.author = model;
                models.add(viewModel);
            }

            refreshData(models);
        }
    };

    // 联系人中找用户的位置坐标
    private int indexOfUserContact(List<UserSampleModel> contact, String userId) {
        int index = 0;
        for (UserSampleModel model : contact) {
            if (model.id.equalsIgnoreCase(userId))
                return index;
            index++;
        }
        return -1;
    }

    @Override
    public void onDataLoaded(List<GroupMemberCard> groupMemberCards) {
        // 成功
        GroupMemberAddContract.View view = getView();
        if (view == null)
            return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                GroupMemberAddContract.View view = getView();
                if (view == null)
                    return;
                view.onAddedSucceed();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final String strRes) {
        // 失败
        GroupMemberAddContract.View view = getView();
        if (view == null)
            return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                GroupMemberAddContract.View view = getView();
                if (view == null)
                    return;
                view.showError(strRes);
            }
        });
    }
}
