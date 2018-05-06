package csx.haha.com.factory.presenter.search;

import csx.haha.com.factory.presenter.BasePresenter;

/**
 * 搜索群的界面实现
 * Created by sunray on 2018-4-29.
 */

public class SearchGroupPresenter extends BasePresenter<SearchContract.GroupView>
    implements SearchContract.Presenter {

    public SearchGroupPresenter(SearchContract.GroupView view) {
        super(view);
    }

    @Override
    public void search(String content) {

    }
}
