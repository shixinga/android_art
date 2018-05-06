package csx.haha.com.factory.presenter.contact;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;


import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.List;

import csx.haha.com.common.widget.recycler.RecyclerAdapter;
import csx.haha.com.factory.data.DataSource;
import csx.haha.com.factory.data.helper.UserHelper;
import csx.haha.com.factory.data.user.ContactDataSource;
import csx.haha.com.factory.data.user.ContactRepository;
import csx.haha.com.factory.model.db.User;
import csx.haha.com.factory.model.db.User_Table;
import csx.haha.com.factory.persistence.Account;
import csx.haha.com.factory.presenter.BasePresenter;
import csx.haha.com.factory.presenter.BaseSourcePresenter;
import csx.haha.com.factory.utils.DiffUiDataCallback;

/**
 * 联系人的Presenter实现
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class ContactPresenter extends BaseSourcePresenter<User, User,
        ContactDataSource, ContactContract.View>
        implements ContactContract.Presenter, DataSource.SucceedCallback<List<User>> {
    public ContactPresenter(ContactContract.View view) {
        // 初始化数据仓库
        super(new ContactRepository(), view);
    }


    @Override
    public void start() {
        super.start();
        //进行本地数据的
        mSource.load(this);

        //加载网络数据
        UserHelper.refreshContacts();
    }

    private void diff(List<User> oldList, List<User> newList ) {
        DiffUtil.Callback callback = new DiffUiDataCallback<>(oldList, newList);

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        getView().getRecyclerAdapter().replace(newList);

        //尝试刷新
        result.dispatchUpdatesTo(getView().getRecyclerAdapter());
        getView().onAdapterDataChanged();
    }


    // 运行到这里的时候是子线程
    @Override
    public void onDataLoaded(List<User> users) {
        // 无论怎么操作，数据变更，最终都会通知到这里来
        final ContactContract.View view = getView();
        if (view == null)
            return;

        RecyclerAdapter<User> adapter = view.getRecyclerAdapter();
        List<User> old = adapter.getItems();

        // 进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 调用基类方法进行界面刷新
        refreshData(result, users);
    }
}
