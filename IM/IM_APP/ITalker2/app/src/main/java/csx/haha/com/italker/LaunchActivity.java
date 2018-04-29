package csx.haha.com.italker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Property;
import android.view.View;

import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.animation.AnimatorListener;
import net.qiujuer.genius.ui.compat.UiCompat;

import csx.haha.com.common.app.BaseActivity;
import csx.haha.com.factory.persistence.Account;
import csx.haha.com.italker.activities.AccountActivity;
import csx.haha.com.italker.activities.MainActivity;
import csx.haha.com.italker.frags.assist.PermissionsFragment;

public class LaunchActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    //Drawable
    private ColorDrawable mBgDrawable;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        //设置根布局的背景颜色
        View root = findViewById(R.id.activity_launch);
        int color = UiCompat.getColor(getResources(), R.color.colorPrimary);
        mBgDrawable = new ColorDrawable(color);
        root.setBackground(mBgDrawable);
    }

    @Override
    protected void initData() {
        super.initData();

        //动画进行到0.5时结束
        startAnim(0.5f, new Runnable() {
            @Override
            public void run() {
                waitPushReceiverId();
            }
        });
    }

    /**
     * 等待个推框架对我们的pushId设置好值
     */
    private void waitPushReceiverId() {
        if (Account.isLogin()) {
            //如果没有绑定则等待广播进行绑定
            if (Account.isBind()) {
                skip();
                return;
            }
        } else {

            //没有登录是不能绑定pushId的
            //如果拿到了pushId
            if (!TextUtils.isEmpty(Account.getPushId())) {
                //跳转
                skip();
                return;
            }

        }


        //循环等待
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                waitPushReceiverId();
            }
        }, 500);
    }

    /**
     * 跳转到MainActivity之前把剩下的50%的动画执行完成
     */
    private void skip() {
        startAnim(1.0f, new Runnable() {
            @Override
            public void run() {
                reallySkip();
            }
        });
    }

    private void reallySkip() {
        /**
         * android6.0以上的手机才会有运行时权限检查
         * 6.0一下的手机在安装时就被赋予权限，所以直接就可以进入MainActivity
         */
        if ( PermissionsFragment.haveAll(this, getSupportFragmentManager())) {
            //检查跳转到主页还是登录
            if (Account.isLogin()) {

                MainActivity.show(this);
            } else {
                AccountActivity.show(this);
            }
            finish();
        }
    }

    /**
     * 给背景设置一个动画
     * @param endProcess 动画结束进度
     * @param endCallback 动画结束时触发
     */
    private void startAnim(float endProcess, final Runnable endCallback) {

        //获取一个最终的颜色
        int finalColor = Resource.Color.WHITE;
        //运算当前进度的颜色
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int endColor = (int) evaluator.evaluate(endProcess, mBgDrawable.getColor(), finalColor);
        //构建一个属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofObject(this, property, evaluator, endColor);
        valueAnimator.setDuration(1500)
                .addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        //动画结束时触发
                        endCallback.run();
                    }
                });
        valueAnimator.start();

    }

    private Property<LaunchActivity, Object> property = new Property<LaunchActivity, Object>(Object.class, "color") {
        @Override
        public Object get(LaunchActivity object) {
            return object.mBgDrawable.getColor();
        }

        @Override
        public void set(LaunchActivity object, Object value) {
            object.mBgDrawable.setColor((Integer) value);
        }
    };
}
