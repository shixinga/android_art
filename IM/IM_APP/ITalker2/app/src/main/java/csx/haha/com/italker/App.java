package csx.haha.com.italker;

import com.igexin.sdk.PushManager;

import csx.haha.com.common.app.MyApplication;
import csx.haha.com.factory.Factory;

/**
 * Created by sunray on 2018-4-24.
 */

public class App extends MyApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        //调用Factory进行初始化（如从SharePreference读取并初始化pushId）
        Factory.setUp();
        //推送进行初始化
        PushManager.getInstance().initialize(this);
    }
}
