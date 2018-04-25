package csx.haha.com.italker.frags.account;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import csx.haha.com.common.app.BaseFragment;
import csx.haha.com.common.widget.PortraitView;
import csx.haha.com.factory.Factory;
import csx.haha.com.factory.net.UploadHelper;
import csx.haha.com.italker.App;
import csx.haha.com.italker.R;
import csx.haha.com.italker.frags.media.GalleryFragment;

import static android.app.Activity.RESULT_OK;

/**
 * 更新信息
 */
public class UpdateInfoFragment extends BaseFragment {
    private static final String TAG = "MainActivity";
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

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
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**
     * 加载Uri到当前的头像中
     * @param resultUri
     */
    private void loadPortrait(Uri resultUri) {
        Glide.with(this)
                .load(resultUri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);

        //拿到本地文件的地址
        final String localPath = resultUri.getPath();
//Log.d(TAG, "loadPortrait: localPath=" + localPath);
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                String url = UploadHelper.uploadPortrait(localPath);
//Log.d(TAG, "loadPortrait: 上传的远程的图片的url=" + url);
            }
        });
    }
}
