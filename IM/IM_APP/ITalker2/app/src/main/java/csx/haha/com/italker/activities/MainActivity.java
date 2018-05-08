package csx.haha.com.italker.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import csx.haha.com.common.Common;
import csx.haha.com.common.app.BaseActivity;
import csx.haha.com.common.widget.PortraitView;
import csx.haha.com.factory.persistence.Account;
import csx.haha.com.italker.R;
import csx.haha.com.italker.activities.AccountActivity;
import csx.haha.com.italker.frags.assist.PermissionsFragment;
import csx.haha.com.italker.frags.main.ActiveFragment;
import csx.haha.com.italker.frags.main.ContactFragment;
import csx.haha.com.italker.frags.main.GroupFragment;
import csx.haha.com.italker.helper.NavHelper;

public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, NavHelper.OnTabChangedListener<Integer> {
    private static final String TAG = "MainActivity";



    @BindView(R.id.appbar)
    View mLayAppbar;

    @BindView(R.id.im_portrait)
    PortraitView mPortraitView;

    @BindView(R.id.txt_title)
    TextView mTvTitle;

    @BindView(R.id.im_search)
    ImageView mIVSearch;

    @BindView(R.id.lay_container)
    FrameLayout mFlayout;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    private NavHelper<Integer> mNavHelper;

    @BindView(R.id.btn_action)
    FloatActionButton mAction;

    /**
     * MainActivity 的显示入口
     * @param context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (Account.isComplete()) {
            Log.d(TAG, "initArgs: complete!!!!!!");

            return super.initArgs(bundle);
        } else {
            Log.d(TAG, "initArgs: nocomplete!!!!!!");
            UserActivity.show(this);
            return false;
        }

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }



    @Override
    protected void initWidget() {
        super.initWidget();
        //初始化底部辅助的工具类
        mNavHelper = new NavHelper(this, getSupportFragmentManager(), R.id.lay_container, this);
        mNavHelper
                .add(R.id.action_home, new NavHelper.Tab<Integer>(ActiveFragment.class, R.string.title_home))
                .add(R.id.action_group, new NavHelper.Tab<Integer>(GroupFragment.class, R.string.title_group))
                .add(R.id.action_contact, new NavHelper.Tab<Integer>(ContactFragment.class, R.string.title_contact));


        mNavigation.setOnNavigationItemSelectedListener(this);

        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .into(new ViewTarget<View, GlideDrawable>(mLayAppbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });


    }

    @Override
    protected void initData() {
        super.initData();
        //从底部导航栏中接管我们的Menu，然后进行手动的触发第一次点击
        Menu menu = mNavigation.getMenu();
        //触发首次选中Home
        menu.performIdentifierAction(R.id.action_home, 0);

        //初始化头像
        mPortraitView.setup(Glide.with(this), Account.getUser());

    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        PersonalActivity.show(this, Account.getUserId());
    }


    @OnClick(R.id.im_search)
    public void onSearchMenuClick() {
        //如果当前是在群的界面，则打开搜索群的界面
        //否则都是打开个人搜索界面
        int type = Objects.equals(mNavHelper.getCurrentTab().extra, R.string.title_group) ?
                SearchActivity.TYPE_GROUP : SearchActivity.TYPE_USER;
        SearchActivity.show(this, type);

    }
    @OnClick(R.id.btn_action)
    public void onActionClick() {
        //浮动按钮点击时，判断当前界面是群界面还是联系人界面
        if (Objects.equals(mNavHelper.getCurrentTab().extra, R.string.title_group)) {
            //打开创建群的界面
            GroupCreateActivity.show(this);

        } else {
            //打开添加用户的界面
            SearchActivity.show(this, SearchActivity.TYPE_USER);
        }
    }


    /**
     * 当底部导航被点击的时候出发这个点击
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Log.d(TAG, "onNavigationItemSelected: " + getSupportFragmentManager().getFragments().size());
        //转接事件流到工具类中，由工具类处理Fragment的切换
        return mNavHelper.performClickMenu(item.getItemId());
    }

    /**
     * NavHelper 处理后回调的方法
     * @param newTab
     * @param oldTab
     */
    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        //从额外字段中取出我们的Title资源Id
        mTvTitle.setText(newTab.extra);

        //对浮动按钮进行隐藏与显示的动画
        float transY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra, R.string.title_home)) {
            transY = Ui.dipToPx(getResources(), 76);
        } else if (Objects.equals(newTab.extra, R.string.title_group)) {
                mAction.setImageResource(R.drawable.ic_group_add);
                rotation = -360;
        } else {
            mAction.setImageResource(R.drawable.ic_contact_add);
            rotation = 360;
        }

        //开始动画
        mAction.animate()
                .rotation(rotation)
                .translationY(transY)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .setDuration(500)
                .start();
    }
}
