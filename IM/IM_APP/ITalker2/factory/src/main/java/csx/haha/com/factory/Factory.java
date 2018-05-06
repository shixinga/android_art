package csx.haha.com.factory;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import csx.haha.com.common.app.MyApplication;
import csx.haha.com.factory.data.DataSource;
import csx.haha.com.factory.data.group.GroupCenter;
import csx.haha.com.factory.data.group.GroupDispatcher;
import csx.haha.com.factory.data.message.MessageCenter;
import csx.haha.com.factory.data.message.MessageDispatcher;
import csx.haha.com.factory.data.user.UserCenter;
import csx.haha.com.factory.data.user.UserDispatcher;
import csx.haha.com.factory.model.api.PushModel;
import csx.haha.com.factory.model.api.RspModel;
import csx.haha.com.factory.model.card.GroupCard;
import csx.haha.com.factory.model.card.GroupMemberCard;
import csx.haha.com.factory.model.card.MessageCard;
import csx.haha.com.factory.model.card.UserCard;
import csx.haha.com.factory.persistence.Account;
import csx.haha.com.factory.utils.DBFlowExclusionStrategy;


/**
 * Created by sunray on 2018-4-25.
 */

public class Factory {
    private static final String TAG = "MainActivity";
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
        if (!Account.isLogin()) {
            return;
        }

        PushModel model = PushModel.decode(msg);
        if (model == null) {
            return;
        }

        Log.d(TAG, "dispatchPush: " + model.toString());
        // 对推送集合进行遍历
        for (PushModel.Entity entity : model.getEntities()) {
            Log.e(TAG, "dispatchPush-Entity:" + entity.toString());

            switch (entity.type) {
                case PushModel.ENTITY_TYPE_LOGOUT:
                    sInstance.logout();
                    // 退出情况下，直接返回，并且不可继续
                    return;

                case PushModel.ENTITY_TYPE_MESSAGE: {
                    // 普通消息
                    MessageCard card = getGson().fromJson(entity.content, MessageCard.class);
                    getMessageCenter().dispatch(card);
                    break;
                }

                case PushModel.ENTITY_TYPE_ADD_FRIEND: {
                    // 好友添加
                    UserCard card = getGson().fromJson(entity.content, UserCard.class);
                    getUserCenter().dispatch(card);
                    break;
                }

                case PushModel.ENTITY_TYPE_ADD_GROUP: {
                    // 添加群
                    GroupCard card = getGson().fromJson(entity.content, GroupCard.class);
                    getGroupCenter().dispatch(card);
                    break;
                }

                case PushModel.ENTITY_TYPE_ADD_GROUP_MEMBERS:
                case PushModel.ENTITY_TYPE_MODIFY_GROUP_MEMBERS: {
                    // 群成员变更, 回来的是一个群成员的列表
                    Type type = new TypeToken<List<GroupMemberCard>>() {
                    }.getType();
                    List<GroupMemberCard> card = getGson().fromJson(entity.content, type);
                    // 把数据集合丢到数据中心处理
                    getGroupCenter().dispatch(card.toArray(new GroupMemberCard[0]));
                    break;
                }
                case PushModel.ENTITY_TYPE_EXIT_GROUP_MEMBERS: {
                    // TODO 成员退出的推送
                }

            }
        }
    }

    /**
     * 获取一个用户中心的实现类
     *
     * @return 用户中心的规范接口
     */
    public static UserCenter getUserCenter() {
        return UserDispatcher.instance();
    }

    /**
     * 获取一个消息中心的实现类
     *
     * @return 消息中心的规范接口
     */
    public static MessageCenter getMessageCenter() {
        return MessageDispatcher.instance();
    }

    /**
     * 获取一个群处理中心的实现类
     *
     * @return 群中心的规范接口
     */
    public static GroupCenter getGroupCenter() {
        return GroupDispatcher.instance();
    }

}
