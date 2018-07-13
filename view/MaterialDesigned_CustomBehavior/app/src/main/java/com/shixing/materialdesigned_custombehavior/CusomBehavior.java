package com.shixing.materialdesigned_custombehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shixing on 2018/5/25.
 */

public class CusomBehavior extends CoordinatorLayout.Behavior<View> {
    public CusomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 用来决定需要监听哪些控件或者容器的状态(1.监听谁 2.什么状态改变)
     * @param parent 父容器
     * @param child 子控件----需要监听别的view，，就是一个观察者
     * @param dependency  被观察者
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //还可以根据id或者tag来判断
//        return dependency instanceof TextView && dependency.getId() == R.id.tv1 || super.layoutDependsOn(parent, child, dependency);
        return dependency instanceof TextView && "tv111".equals(dependency.getTag()) || super.layoutDependsOn(parent, child, dependency);
    }

    /**
     * 当被监听的view改变时回调
     * 可以在里面做联动动画
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //获取被监听的view的状态----垂直方向
        int offset = dependency.getTop() - child.getTop();
        //让child（观察者）
        ViewCompat.offsetTopAndBottom(child, offset);
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
