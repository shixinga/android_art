package csx.haha.com.italker.frags.search;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.drawable.LoadingCircleDrawable;
import net.qiujuer.genius.ui.drawable.LoadingDrawable;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import csx.haha.com.common.app.PresenterFragment;
import csx.haha.com.common.widget.EmptyView;
import csx.haha.com.common.widget.PortraitView;
import csx.haha.com.common.widget.recycler.RecyclerAdapter;
import csx.haha.com.factory.model.card.UserCard;
import csx.haha.com.factory.persistence.Account;
import csx.haha.com.factory.presenter.BaseContract;
import csx.haha.com.factory.presenter.contact.FollowContract;
import csx.haha.com.factory.presenter.contact.FollowPresenter;
import csx.haha.com.factory.presenter.search.SearchContract;
import csx.haha.com.factory.presenter.search.SearchUserPresenter;
import csx.haha.com.italker.R;
import csx.haha.com.italker.activities.PersonalActivity;
import csx.haha.com.italker.activities.SearchActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchUserFragment extends PresenterFragment<SearchContract.Presenter>
    implements SearchActivity.ISearchFragment, SearchContract.UserView {


    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private RecyclerAdapter<UserCard> mAdapter;

    public SearchUserFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_user;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        //初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter() {
            @Override
            protected int getItemViewType(int position, Object data) {
                //返回cell的布局id
                return R.layout.cell_search_list;
            }

            @Override
            protected MyViewHolder myOnCreateViewHolder(View root, int viewType) {
                return new SearchUserFragment.ViewHolder(root);
            }
        });

        //初始化占位布局
        mEmptyView.bind(mRecycler);
        setPlaceHolderView(mEmptyView);
    }

    @Override
    protected void initData() {
        super.initData();
        // 发起首次搜索
        search("");
    }

    @Override
    public void search(String content) {
        mPresenter.search(content);
    }

    @Override
    protected void initPresenter() {
        new SearchUserPresenter(this);
    }

    @Override
    public void onSearchDone(List<UserCard> userCards) {

        //数据成功的情况下返回数据
        mAdapter.replace(userCards);
        //如果有数据，则是ok，没有数据就显示空布局
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }

    class ViewHolder extends RecyclerAdapter.MyViewHolder<UserCard>
        implements FollowContract.View {
        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.im_follow)
        ImageView mFollow;

        private FollowContract.Presenter mPresenter;

        public ViewHolder(View itemView) {
            super(itemView);
            //每个显示的rootview都有一个presenter （mvp模式）
            new FollowPresenter(this);
        }

        @Override
        protected void onBind(UserCard data) {

            mPortraitView.setup(Glide.with(SearchUserFragment.this), data);
            mName.setText(data.getName());
            mFollow.setEnabled(!data.isFollow());
        }

        @OnClick(R.id.im_follow)
        void onFollowClick() {
            //发起关注
            mPresenter.follow(mData.getId());
        }

        @Override
        public void showError(String str) {
            // 更改当前界面状态
            if (mFollow.getDrawable() instanceof LoadingDrawable) {
                // 失败则停止动画，并且显示一个圆圈
                LoadingDrawable drawable = (LoadingDrawable) mFollow.getDrawable();
                drawable.setProgress(1);
                drawable.stop();
            }
        }

        @Override
        public void showLoading() {
            int minSize = (int) Ui.dipToPx(getResources(), 22);
            int maxSize = (int) Ui.dipToPx(getResources(), 30);
            // 初始化一个圆形的动画的Drawable
            LoadingDrawable drawable = new LoadingCircleDrawable(minSize, maxSize);
            drawable.setBackgroundColor(0);

            int[] color = new int[]{UiCompat.getColor(getResources(), R.color.white_alpha_208)};
            drawable.setForegroundColor(color);
            // 设置进去
            mFollow.setImageDrawable(drawable);
            // 启动动画
            drawable.start();
        }


        @Override
        public void setPresenter(FollowContract.Presenter presenter) {
            mPresenter = presenter;
        }

        @Override
        public void onFollowSucceed(UserCard userCard) {
            // 更改当前界面状态
            if (mFollow.getDrawable() instanceof LoadingDrawable) {
                ((LoadingDrawable) mFollow.getDrawable()).stop();
                // 设置为默认的
                mFollow.setImageResource(R.drawable.sel_opt_done_add);
            }
            // 发起更新
            updateData(userCard);
        }


        //打开该用户的详情介绍
        @OnClick(R.id.im_portrait)
        void onPortraitClick() {
            PersonalActivity.show(getActivity(), mData.getId());
        }
    }
}
