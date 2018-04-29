package csx.haha.com.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import csx.haha.com.common.app.MyApplication;
import csx.haha.com.factory.data.DataSource;
import csx.haha.com.factory.model.api.RspModel;
import csx.haha.com.factory.persistence.Account;
import csx.haha.com.factory.utils.DBFlowExclusionStrategy;

/**
 * Created by sunray on 2018-4-25.
 */

public class Factory {

    private static final Factory sInstance;
    //全局的exeutor
    private Executor executor;
    //全局的Gson
    private final Gson gson;


    static {
        sInstance = new Factory();
    }

    private Factory() {
        executor = Executors.newFixedThreadPool(4);
        gson = new GsonBuilder()
                //设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                //设置一个过滤器,数据库级别的Model不进行Json转换
                .setExclusionStrategies(new DBFlowExclusionStrategy())
                .create();
    }

    /**
     * 初始化
     */
    public static void setUp() {
        FlowManager.init(new FlowConfig.Builder(MyApplication.getInstance())
            .openDatabasesOnInit(true) //数据库初始化的时候就打开
            .build());

        //初始化数据（pushId）
        Account.load(MyApplication.getInstance());

    }
    public static Factory getInstance() {
        return sInstance;
    }

    /**
     * 异步运行的方法
     *
     * @param runnable Runnable
     */
    public static void runOnAsync(Runnable runnable) {
        // 拿到单例，拿到线程池，然后异步执行
        sInstance.executor.execute(runnable);
    }

    public static Gson getGson() {
        return sInstance.gson;
    }

    /**
     * 输出网络返回的信息
     * @param model
     * @param callback
     */
    public static void decodeRspCode(RspModel model, DataSource.FailedCallback callback) {
        if (model == null) {
            return;
        }

        // 进行Code区分
        switch (model.getCode()) {
            case RspModel.SUCCEED:
                return;
            case RspModel.ERROR_SERVICE:
                decodeRspCode("服务器异常", callback);
                break;
            case RspModel.ERROR_NOT_FOUND_USER:
                decodeRspCode("找不到该用户", callback);
                break;
            case RspModel.ERROR_NOT_FOUND_GROUP:
                decodeRspCode("找不到该群", callback);
                break;
            case RspModel.ERROR_NOT_FOUND_GROUP_MEMBER:
                decodeRspCode("找不到该群成员", callback);
                break;
            case RspModel.ERROR_CREATE_USER:
                decodeRspCode("创建用户失败", callback);
                break;
            case RspModel.ERROR_CREATE_GROUP:
                decodeRspCode("创建群失败", callback);
                break;
            case RspModel.ERROR_CREATE_MESSAGE:
                decodeRspCode("不能创建信息", callback);
                break;
            case RspModel.ERROR_PARAMETERS:
                decodeRspCode("参数错误", callback);
                break;
            case RspModel.ERROR_PARAMETERS_EXIST_ACCOUNT:
                decodeRspCode("参数错误，已存在该帐号", callback);
                break;
            case RspModel.ERROR_PARAMETERS_EXIST_NAME:
                decodeRspCode("参数错误，已存在该用户名", callback);
                break;
            case RspModel.ERROR_ACCOUNT_TOKEN:
                MyApplication.showToast("重新登录 ..@_@|||||.");
                sInstance.logout();
                break;
            case RspModel.ERROR_ACCOUNT_LOGIN:
                decodeRspCode("登录错误", callback);
                break;
            case RspModel.ERROR_ACCOUNT_REGISTER:
                decodeRspCode("注册失败", callback);
                break;
            case RspModel.ERROR_ACCOUNT_NO_PERMISSION:
                decodeRspCode("你没有权限", callback);
                break;
            case RspModel.ERROR_UNKNOWN:
            default:
                decodeRspCode("未知错误", callback);
                break;
        }
    }

    private static void decodeRspCode(final String str, final DataSource.FailedCallback callback) {
        if (callback != null) {
            callback.onDataNotAvailable(str);
        }
    }

    /**
     * 收到账户退出的消息需要进行账户退出重新登录
     */
    private void logout() {

    }

    /**
     * 处理推送过来的消息
     * @param msg
     */
    public static void dispatchPush(String msg) {

    }
}
