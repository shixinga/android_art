package csx.haha.com.italker.frags.message;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.OnClick;
import csx.haha.com.common.widget.PortraitView;
import csx.haha.com.factory.model.db.User;
import csx.haha.com.factory.presenter.message.ChatContract;
import csx.haha.com.factory.presenter.message.ChatUserPresenter;
import csx.haha.com.italker.R;
import csx.haha.com.italker.activities.PersonalActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatUserFragment extends ChatFragment<User>
    implements ChatContract.UserView {
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    private MenuItem mUserInfoMenuItem;

    public ChatUserFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_user;
    }

    @Override
    protected void initPresenter() {
        new ChatUserPresenter(this, mReceiverId);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.inflateMenu(R.menu.chat_user);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_person) {
                    onPortraitClick();
                }
                return false;
            }
        });

        // 拿到菜单Icon
        mUserInfoMenuItem = mToolbar.getMenu().findItem(R.id.action_person);
    }

    @OnClick(R.id.im_portrait)
    public void onPortraitClick() {
        PersonalActivity.show(getContext(), mReceiverId);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        super.onOffsetChanged(appBarLayout, verticalOffset);
        //完全展开
        if (verticalOffset == 0) {
            mPortrait.setVisibility(View.VISIBLE);
            mPortrait.setScaleX(1);
            mPortrait.setScaleY(1);
            mPortrait.setAlpha(1);

            // 隐藏菜单
            mUserInfoMenuItem.setVisible(false);
            mUserInfoMenuItem.getIcon().setAlpha(0);
        } else {
            // abs 运算
            verticalOffset = Math.abs(verticalOffset);
            final int totalScrollRange = appBarLayout.getTotalScrollRange();
            if (verticalOffset >= totalScrollRange) {
                // 关闭状态
                mPortrait.setVisibility(View.INVISIBLE);
                mPortrait.setScaleX(0);
                mPortrait.setScaleY(0);
                mPortrait.setAlpha(0);

                // 显示菜单
                mUserInfoMenuItem.setVisible(true);
                mUserInfoMenuItem.getIcon().setAlpha(255);

            } else {
                // 中间状态
                float progress = 1 - verticalOffset / (float) totalScrollRange;
                mPortrait.setVisibility(View.VISIBLE);
                mPortrait.setScaleX(progress);
                mPortrait.setScaleY(progress);
                mPortrait.setAlpha(progress);
                // 和头像恰好相反
                mUserInfoMenuItem.setVisible(true);
                mUserInfoMenuItem.getIcon().setAlpha(255 - (int) (255 * progress));
            }
        }
    }

    @Override
    public void onInit(User user) {

    }
}
