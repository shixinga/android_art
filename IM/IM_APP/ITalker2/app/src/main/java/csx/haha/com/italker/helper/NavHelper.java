package csx.haha.com.italker.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;

/**
 * 解决对Fragment的调度与重用问题。
 * 最优的Fragment切换
 * Created by sunray on 2018-4-23.
 */

public class NavHelper<T> {
    private static final String TAG = "MainActivity";
    //所有的Tab集合
    private final SparseArray<Tab<T>> tabs = new SparseArray();

    private final FragmentManager fragmentManager;
    private final int containerId;
    private final Context context;
    private final OnTabChangedListener<T> listener;

    //当前选中的Tab
    private Tab<T> currentTab;

    public NavHelper(Context context,
                     FragmentManager fragmentManager,
                     int containerId, OnTabChangedListener<T> listener) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
        this.context = context;
        this.listener = listener;
    }

    /**
     * 添加Tab
     * @param menuId Tab对应的菜单id
     * @param tab Tab
     */
    public NavHelper<T> add(int menuId, Tab<T> tab) {
        tabs.put(menuId,tab);
        return this;
    }

    /**
     * 获取当前的显示的Tab
     * @return
     */
    public Tab<T> getCurrentTab() {
        return currentTab;
    }

    /**
     * 执行点击菜单的操作
     * @param menuId 菜单的id
     * @return 能否处理这个点击事件
     *
     */
    public boolean performClickMenu(int menuId) {

        //集合中寻找点击的菜单对应的Tab，
        //如果有则进行处理
        Tab<T> tab = tabs.get(menuId);
        if (tab != null) {
            doSelect(tab);
            return true;
        }
        return false;
    }

    /**
     * 进行这是的Tab选中操作
     * @param tab
     */
    private void doSelect(Tab<T> tab) {
        Tab<T> oldTab = null;

        if (currentTab != null) {
            oldTab = currentTab;
            if (oldTab == tab) {
                //如果说当前的Tab就是点击的Tab
                //那么我们不做处理
                notifyTabReselect(tab);
                return;
            }
        }
        //复制并调用切换方法
        currentTab = tab;
        doTabChanged(currentTab, oldTab);
    }

    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {
        Log.d(TAG, "NavHelper doTabChanged: ");
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (oldTab != null) {
            if (oldTab.fragment != null) {
                //从界面移除，但是还在Fragment的缓存空间中
                ft.detach(oldTab.fragment); //fragment还在内存中
            }
        }
        if (newTab != null) {
            if (newTab.fragment == null) {
                //首次创建
                Fragment fragment = Fragment.instantiate(context, newTab.clazz.getName(), null);
                //缓存起来
                newTab.fragment = fragment;
                //提交到FragmentManager
                ft.add(containerId, fragment, newTab.clazz.getName());
            } else {
                //从FragmentManager的缓存空间中重新加载到界面中
                ft.attach(newTab.fragment);
            }
        }
        ft.commit();
        //通知回调
        notifyTabSelect(newTab, oldTab);
    }

    private void notifyTabSelect(Tab<T> newTab, Tab<T> oldTab) {
        // 二次点击Tab所做的操作
        if (listener != null) {
            listener.onTabChanged(newTab, oldTab);
        }

    }
    private void notifyTabReselect(Tab<T> tab) {
        // 二次点击Tab所做的操作

    }

    /**
     * 所有的Tab的基础属性
     * @param <T>
     */
    public static class  Tab<T> {

        public Tab(Class<?> clazz, T extra) {
            this.clazz = clazz;
            this.extra = extra;
        }

        public Class<?> clazz;
        //额外的字段，用户自己设定需要使用
        public T extra;

        //内部缓存的对应的Fragment
        //Package权限， 外部无法使用
        Fragment fragment;
    }

    /**
     * 定义事件处理完成后的回调接口
     * @param <T>
     */
    public interface OnTabChangedListener<T> {
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }
}
