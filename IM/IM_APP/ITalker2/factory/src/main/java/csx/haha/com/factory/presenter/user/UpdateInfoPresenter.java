package csx.haha.com.factory.presenter.user;

import android.text.TextUtils;
import android.util.Log;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import csx.haha.com.factory.Factory;
import csx.haha.com.factory.data.DataSource;
import csx.haha.com.factory.data.helper.UserHelper;
import csx.haha.com.factory.model.api.user.UserUpdateModel;
import csx.haha.com.factory.model.card.UserCard;
import csx.haha.com.factory.model.db.User;
import csx.haha.com.factory.net.UploadHelper;
import csx.haha.com.factory.presenter.BasePresenter;

/**
 * Created by sunray on 2018-4-28.
 */

public class UpdateInfoPresenter extends BasePresenter<UpdateInfoContract.View>
    implements UpdateInfoContract.Presenter, DataSource.Callback<UserCard>{
    private static final String TAG = "MainActivity";
    public UpdateInfoPresenter(UpdateInfoContract.View view) {
        super(view);
    }

    @Override
    public void update(final String photoFilePath, final String desc, final boolean isMan) {
        start();

        final UpdateInfoContract.View view = getView();

        if (TextUtils.isEmpty(photoFilePath) || TextUtils.isEmpty(desc)) {
            view.showError("头像或者描述不能为空");
        } else {
            //上传头像
            Factory.runOnAsync(new Runnable() {
                @Override
                public void run() {
                    String url = UploadHelper.uploadPortrait(photoFilePath);
                    if (TextUtils.isEmpty(url)) {
                        // 上传失败
                        view.showError("上传图片失败");
                    } else {
                        UserUpdateModel model = new UserUpdateModel("", url, desc,
                                isMan ? User.SEX_MAN : User.SEX_WOMAN);
                        UserHelper.update(model, UpdateInfoPresenter.this);
                    }

                }
            });

        }
    }

    @Override
    public void onDataLoaded(UserCard userCard) {
        final UpdateInfoContract.View view = getView();
Log.d(TAG, "UpdateInfoPresenter onDataLoaded()：" + userCard + ", view=" + view);
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.updateSucceed();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final String strRes) {
        final UpdateInfoContract.View view = getView();
Log.d(TAG, "UpdateInfoPresenter onDataNotAvailable()：view=" + view);
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
    }
}
