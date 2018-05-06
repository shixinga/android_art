package csx.haha.com.factory.presenter;

/**
 * Created by sunray on 2018-4-26.
 */

public class BasePresenter<T extends BaseContract.IView> implements BaseContract.IPresenter {

    protected T mView;
    public BasePresenter(T view) {
        setView(view);
    }
    @Override
    public void start() {
        if (mView != null) {
            mView.showLoading();
        }
    }

    @Override
    public void destroy() {
        if (mView != null) {
            mView.setPresenter(null);
            mView = null;
        }
    }

    protected void setView(T view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    /**
     * 给子类使用的获取View的操作
     * @return
     */
    protected T getView() {
        return mView;
    }
}
