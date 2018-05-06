package csx.haha.com.factory.presenter;

import java.util.List;

import csx.haha.com.factory.data.DataSource;
import csx.haha.com.factory.data.DbDataSource;

/**
 * Created by shixing on 2018/5/4.
 */

public abstract class BaseSourcePresenter<Data, ViewModel, Source extends DbDataSource<Data>,
        View extends BaseContract.RecyclerView>
    extends BaseRecyclerPresenter<ViewModel, View>
    implements DataSource.SucceedCallback<List<Data>> {
    protected Source mSource;
    public BaseSourcePresenter(Source source, View view) {
        super(view);
        this.mSource = source;
    }

    @Override
    public void start() {
        super.start();
        if (mSource != null) {
            mSource.load(this);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        mSource.dispose();
        mSource = null;
    }


}
