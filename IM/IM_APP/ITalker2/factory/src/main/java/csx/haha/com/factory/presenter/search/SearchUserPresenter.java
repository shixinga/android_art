package csx.haha.com.factory.presenter.search;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import csx.haha.com.factory.data.DataSource;
import csx.haha.com.factory.data.helper.UserHelper;
import csx.haha.com.factory.model.card.UserCard;
import csx.haha.com.factory.presenter.BasePresenter;
import retrofit2.Call;

/**
 * 搜索用户的界面实现
 * Created by sunray on 2018-4-29.
 */

public class SearchUserPresenter extends BasePresenter<SearchContract.UserView>
    implements SearchContract.Presenter, DataSource.Callback<List<UserCard>> {

    private Call searchCall;
    public SearchUserPresenter(SearchContract.UserView view) {
        super(view);
    }

    @Override
    public void search(String content) {
        start();

        if (searchCall != null && !searchCall.isCanceled()) {
            //如果有上一次的请求，并且还没有被取消，则取消上一次的请求
            searchCall.cancel();
        }
        searchCall = UserHelper.search(content, this);
    }

    @Override
    public void onDataLoaded(final List<UserCard> userCards) {
        //搜索成功
        final SearchContract.UserView view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.onSearchDone(userCards);
                }
            });
        }
    }

    @Override
    public void onDataNotAvailable(final String strRes) {
        //搜索失败
        final  SearchContract.UserView view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(strRes);
                }
            });
        }

    }
}
