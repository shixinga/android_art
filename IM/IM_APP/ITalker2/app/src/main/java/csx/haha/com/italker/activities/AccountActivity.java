package csx.haha.com.italker.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;
import csx.haha.com.common.app.BaseActivity;
import csx.haha.com.italker.R;
import csx.haha.com.italker.frags.account.AccountTrigger;
import csx.haha.com.italker.frags.account.LoginFragment;
import csx.haha.com.italker.frags.account.RegisterFragment;

public class AccountActivity extends BaseActivity implements AccountTrigger {
    private static final String TAG = "MainActivity";
    private Fragment mCurFragment;
    private Fragment mLoginFragment;
    private Fragment mRegisterFragment;

    @BindView(R.id.im_bg)
    ImageView mBg;

    /**
     * 账户Activity显示的入口
     * @param context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mCurFragment = mLoginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurFragment)
                .commit();
Log.d(TAG, "AccountActivity initWidget: ");
        //初始化背景
        Glide.with(this)
                .load(R.drawable.bg_src_tianjin)
                .centerCrop()
                .into(new ViewTarget<ImageView, GlideDrawable>(mBg) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        //拿到glide的Drawable
                        Drawable drawable = resource.getCurrent();
                        //使用适配类进行包装
//                        drawable = DrawableCompat.wrap(drawable);
//                        drawable.setColorFilter(
//                                UiCompat.getColor(getResources(), R.color.colorAccent)
//                            , PorterDuff.Mode.SCREEN); //设置着色的效果和颜色，
                        this.view.setImageDrawable(drawable);
                    }
                });
    }

    @Override
    public void triggerView() {
        Fragment fragment = null;
        if (mCurFragment == mLoginFragment) {
            //默认情况为null
            //第一次之后就不为null了
            if (mRegisterFragment == null) {
                mRegisterFragment = new RegisterFragment();
            }
            fragment = mRegisterFragment;
        } else {
            //因为默认情况下，mLoginFragment已经被赋值，所以无须判null
            fragment = mLoginFragment;
        }
        mCurFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_container, fragment)
                .commit();
Log.d(TAG, "AccountActivity triggerView: " + fragment);
    }
}
