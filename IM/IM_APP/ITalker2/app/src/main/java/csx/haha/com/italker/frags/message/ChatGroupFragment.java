package csx.haha.com.italker.frags.message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import csx.haha.com.factory.model.db.Group;
import csx.haha.com.factory.presenter.message.ChatContract;
import csx.haha.com.italker.R;

/**
 * 群聊天界面
 * A simple {@link Fragment} subclass.
 */
public class ChatGroupFragment extends ChatFragment<Group>
        implements ChatContract.GroupView {


    public ChatGroupFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_group;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onInit(Group group) {

    }
}
