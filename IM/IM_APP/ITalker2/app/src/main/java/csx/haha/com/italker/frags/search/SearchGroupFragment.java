package csx.haha.com.italker.frags.search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import csx.haha.com.common.app.BaseFragment;
import csx.haha.com.common.app.PresenterFragment;
import csx.haha.com.factory.model.card.GroupCard;
import csx.haha.com.factory.presenter.search.SearchContract;
import csx.haha.com.factory.presenter.search.SearchGroupPresenter;
import csx.haha.com.italker.R;
import csx.haha.com.italker.activities.SearchActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchGroupFragment extends PresenterFragment<SearchContract.Presenter>
    implements SearchActivity.ISearchFragment, SearchContract.GroupView {


    public SearchGroupFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    public void search(String content) {

    }

    @Override
    protected void initPresenter() {
        new SearchGroupPresenter(this);
    }

    @Override
    public void onSearchDone(List<GroupCard> groupCards) {

    }
}
