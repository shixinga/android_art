package csx.haha.com.italker.frags.user;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.genius.ui.widget.Loading;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import csx.haha.com.common.app.MyApplication;
import csx.haha.com.common.app.PresenterFragment;
import csx.haha.com.common.widget.PortraitView;
import csx.haha.com.factory.Factory;
import csx.haha.com.factory.net.UploadHelper;
import csx.haha.com.factory.presenter.user.UpdateInfoContract;
import csx.haha.com.factory.presenter.user.UpdateInfoPresenter;
import csx.haha.com.italker.App;
import csx.haha.com.italker.R;
import csx.haha.com.italker.activities.MainActivity;
import csx.haha.com.italker.frags.media.GalleryFragment;

import static android.app.Activity.RESULT_OK;

/**
 * 更新信息
 */
public class UpdateInfoFragment extends PresenterFragment<UpdateInfoContract.Presenter>
    implements UpdateInfoContract.View{
    private static final String TAG = "MainActivity";
    @BindView(R.id.im_sex)
    ImageView mSex;

    @BindView(R.id.edit_desc)
    EditText mDesc;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    // 头像的本地路径
    private String mPortraitPath;
    private boolean isMan = true;

    public UpdateInfoFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        new GalleryFragment()
                .setListener(new GalleryFragment.OnSelectedListener() {
                    @Override
                    public void onSelectedImage(String path) {
                        UCrop.Options options = new UCrop.Options();
                        //设置图片处理的格式JPEG
                        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                        //设置压缩后的图片精度
                        options.setCompressionQuality(96);

                        //得到头像的缓存地址
                        File dPath = App.getPortraitTmpFile();
                        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                                .withAspectRatio(1, 1) //1 : 1 比例
                                .withMaxResultSize(520, 520) //返回最大的尺寸
                                .withOptions(options) //相关参数
                                .start(getActivity());
                    }
                })
                //show()的时候建议使用getChildFragmentManager,
                //tag 为GalleryFragment.class 名
                .show(getChildFragmentManager(), GalleryFragment.class.getName());
    }

    /**
     * 接收AccountActivity.onActivityResult()传过来的回调，并取出其中的值进行图片加载
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
//            Log.d(TAG, "onActivityResult: " + resultUri.getPath() + "  toString()=" + resultUri);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            MyApplication.showToast("服务器异常了");
//            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**
     * 加载Uri到当前的头像中
     * @param resultUri
     */
    private void loadPortrait(Uri resultUri) {
        // 得到头像地址
        mPortraitPath = resultUri.getPath();
Log.d(TAG, "loadPortrait: localPath=" + mPortraitPath);
        Glide.with(this)
                .load(resultUri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);

//        //拿到本地文件的地址
//        final String localPath = resultUri.getPath();
//Log.d(TAG, "loadPortrait: localPath=" + localPath);
//        Factory.runOnAsync(new Runnable() {
//            @Override
//            public void run() {
//                String url = UploadHelper.uploadPortrait(localPath);
//Log.d(TAG, "loadPortrait: 上传的远程的图片的url=" + url);
//            }
//        });
    }
    @OnClick(R.id.im_sex)
    void onSexClick() {
        // 性别图片点击的时候触发
        isMan = !isMan; // 反向性别

        Drawable drawable = getResources().getDrawable(isMan ?
                R.drawable.ic_sex_man : R.drawable.ic_sex_woman);
        mSex.setImageDrawable(drawable);
        // 设置背景的层级，切换颜色
        mSex.getBackground().setLevel(isMan ? 0 : 1);
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String desc = mDesc.getText().toString();
        // 调用P层进行注册
        mPresenter.update(mPortraitPath, desc, isMan);
    }

    @Override
    public void showError(String str) {
        super.showError(str);
        // 当需要显示错误的时候触发，一定是结束了

        // 停止Loading
        mLoading.stop();
        // 让控件可以输入
        mDesc.setEnabled(true);
        mPortrait.setEnabled(true);
        mSex.setEnabled(true);
        // 提交按钮可以继续点击
        mSubmit.setEnabled(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();

        // 正在进行时，正在进行注册，界面不可操作
        // 开始Loading
        mLoading.start();
        // 让控件不可以输入
        mDesc.setEnabled(false);
        mPortrait.setEnabled(false);
        mSex.setEnabled(false);
        // 提交按钮不可以继续点击
        mSubmit.setEnabled(false);
    }

    @Override
    protected void initPresenter() {
        new UpdateInfoPresenter(this);
    }

    @Override
    public void updateSucceed() {
        // 更新成功跳转到主界面
        MainActivity.show(getContext());
        getActivity().finish();
    }
}
