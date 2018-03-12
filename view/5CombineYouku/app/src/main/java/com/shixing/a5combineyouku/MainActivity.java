package com.shixing.a5combineyouku;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.shixing.a5combineyouku.utils.AnimationUtil;
import com.shixing.a5combineyouku.utils.AnimatorUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = "MainActivity";
    RelativeLayout mRl_level1;
    RelativeLayout mRl_level2;
    RelativeLayout mRl_level3;
    ImageButton mIb_home;
    ImageButton mIb_menu;

    boolean mIsLevel1Display = true;
    boolean mIsLevel2Display = true;
    boolean mIsLevel3Display = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRl_level1 = (RelativeLayout) findViewById(R.id.rl_level1);
        mRl_level2 = (RelativeLayout) findViewById(R.id.rl_level2);
        mRl_level3 = (RelativeLayout) findViewById(R.id.rl_level3);

        mIb_home = (ImageButton) findViewById(R.id.ib_home);
        mIb_menu = (ImageButton) findViewById(R.id.ib_menu);

        mIb_home.setOnClickListener(this);
        mIb_menu.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //AnimationListener 的作用是监听动画是否完成
        if (AnimationUtil.runningAnimationCount != 0) {
            //放弃该事件处理，即不让新的点击动画运行
            return ;
        }
        switch (v.getId()) {
            case R.id.ib_home:
                Log.d(TAG, "onClick: ");
                //布局动画
/*                int delay = 0;
                if (mIsLevel2Display) {
                    //if 第3级菜单有显示，先让其转出，延迟200毫秒后第2级菜单再移出
                    if (mIsLevel3Display) {
                        AnimationUtil.animationOut(mRl_level3,0);
                        mIsLevel3Display = !mIsLevel3Display;
                    }
                    AnimationUtil.animationOut(mRl_level2,delay);
                } else {
                    AnimationUtil.animationIn(mRl_level2);
                }*/
//              属性动画
                if (mIsLevel2Display) {
                    AnimatorUtil.animatorOut(mRl_level2);
                } else {
                    AnimatorUtil.animatorIn(mRl_level2);
                }

                mIsLevel2Display = !mIsLevel2Display;
                break;
            case R.id.ib_menu :
                Log.d(TAG, "onClick: menu");
               /* if (mIsLevel3Display) {
                    AnimationUtil.animationOut(mRl_level3,0);
                } else {
                    AnimationUtil.animationIn(mRl_level3);
                }*/

                //属性动画
                if (mIsLevel3Display) {
                    AnimatorUtil.animatorOut(mRl_level3);
                } else {
                    AnimatorUtil.animatorIn(mRl_level3);
                }
                mIsLevel3Display = !mIsLevel3Display;
                break;
            default:
                break;
        }
    }

}
