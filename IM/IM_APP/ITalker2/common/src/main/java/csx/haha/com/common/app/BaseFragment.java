package csx.haha.com.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sunray on 2018-4-17.
 */

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "MainActivity";
    protected View mRoot;
    protected Unbinder mRootUnBinder;

    @Override
    public void onAttach(Context context) {
//Log.d(TAG, "onAttach: " + this);
        super.onAttach(context);
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//Log.d(TAG, "onCreateView: " + this);
        if (mRoot == null) {
            int layId = getContentLayoutId();
            //初始化当前的根布局，但是不在创建时就add到container里面，否则会报viewParent已经有view了的Exception
            View root = inflater.inflate(layId, container, false);
            initWidget(root);
            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }

        return mRoot;
    }

   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
Log.d(TAG, "onCreate: " + this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
Log.d(TAG, "onActivityCreated: " + this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onStop() {
Log.d(TAG, "onStop: " + this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
Log.d(TAG, "onDestroy: " + this);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
Log.d(TAG, "onDetach: " + this);
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
Log.d(TAG, "onDestroyView: " + this);
        super.onDestroyView();
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//Log.d(TAG, "onViewCreated: " + this);
        super.onViewCreated(view, savedInstanceState);
        //当view创建完成后对数据进行初始化
        initData();
    }

    /**
     * 初始化相关参数
     * @param bundle
     * @return 初始化是否成功,成功返回true
     */
    protected void initArgs(Bundle bundle) {
    }

    /**
     * 得到当前Fragment的layout id
     * @return 当前Fragment的layout id
     */
    protected abstract int getContentLayoutId();


    /**
     * 初始化控件
     */
    protected void initWidget(View root) {
        mRootUnBinder = ButterKnife.bind(this, root);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 返回按键触发时调用
     * @return true代表我已处理返回logic，Activity不用自己finish
     * false代表我没有处理返回logic，Activity自己走自己的logic
     */
    public boolean onBackPressed() {
        return false;
    }

}
