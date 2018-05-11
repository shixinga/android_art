package csx.haha.com.italker.frags.main;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import csx.haha.com.common.app.PresenterFragment;
import csx.haha.com.common.widget.EmptyView;
import csx.haha.com.common.widget.GalleryView;
import csx.haha.com.common.widget.PortraitView;
import csx.haha.com.common.widget.recycler.RecyclerAdapter;
import csx.haha.com.face.Face;
import csx.haha.com.factory.model.db.Session;
import csx.haha.com.factory.presenter.message.SessionContract;
import csx.haha.com.factory.presenter.message.SessionPresenter;
import csx.haha.com.italker.R;
import csx.haha.com.italker.activities.MessageActivity;
import csx.haha.com.utils.DateTimeUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveFragment extends PresenterFragment<SessionContract.Presenter>
    implements SessionContract.View {

    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    // 适配器，User，可以直接从数据库查询数据
    private RecyclerAdapter<Session> mAdapter;
    public ActiveFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        // 初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<Session>() {
                @Override
                protected int getItemViewType(int position, Session session) {
                    // 返回cell的布局id
                    return R.layout.cell_chat_list;
                }

                @Override
                protected ViewHolder myOnCreateViewHolder(View root, int viewType) {
                    return new ActiveFragment.ViewHolder(root);
                }
        });

        // 点击事件监听
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Session>() {
            @Override
            public void onItemClick(RecyclerAdapter.MyViewHolder holder, Session session) {
                // 跳转到聊天界面
                MessageActivity.show(getContext(), session);
            }
        });

        // 初始化占位布局
        mEmptyView.bind(mRecycler);
        setPlaceHolderView(mEmptyView);

    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }


    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        // 进行一次数据加载
        mPresenter.start();
    }

    @Override
    protected void initPresenter() {
        new SessionPresenter(this);
    }

    @Override
    public RecyclerAdapter<Session> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }

    // 界面数据渲染
    class ViewHolder extends RecyclerAdapter.MyViewHolder<Session> {
        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.txt_content)
        TextView mContent;

        @BindView(R.id.txt_time)
        TextView mTime;

        ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Session session) {
            mPortraitView.setup(Glide.with(ActiveFragment.this), session.getPicture());
            mName.setText(session.getTitle());

            String str = TextUtils.isEmpty(session.getContent()) ? "" : session.getContent();
            Spannable spannable = new SpannableString(str);
            // 解析表情
            Face.decode(mContent, spannable, (int) mContent.getTextSize());
            // 把内容设置到布局上
            mContent.setText(spannable);

            mTime.setText(DateTimeUtil.getSampleDate(session.getModifyAt()));
        }
    }
}
