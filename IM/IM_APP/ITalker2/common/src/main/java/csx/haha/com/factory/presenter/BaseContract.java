package csx.haha.com.factory.presenter;

import android.support.annotation.StringRes;

import csx.haha.com.common.widget.recycler.RecyclerAdapter;

/**
 * MVP模式中公共的基本契约
 * Created by sunray on 2018-4-26.
 */

public interface BaseContract {

    interface IView<T extends IPresenter> {

        //公共的：显示一个字符串错误
        void showError(String str);

        //公共的：显示进度条
        void showLoading();

        //支持设置一个Presenter
        void setPresenter(T presenter);

    }

    interface IPresenter {


        //共用的开始触发
        void start();

        //共用的销毁触发
        void destroy();

    }

    // 基本的一个列表的View的职责
    interface RecyclerView<T extends IPresenter, ViewMode> extends IView<T> {
        // 界面端只能刷新整个数据集合，不能精确到每一条数据更新
        // void onDone(List<User> users);

        // 拿到一个适配器，然后自己自主的进行刷新
        RecyclerAdapter<ViewMode> getRecyclerAdapter();

        // 当适配器数据更改了的时候触发
        void onAdapterDataChanged();
    }
}