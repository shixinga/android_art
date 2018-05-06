package csx.haha.com.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import csx.haha.com.common.app.MyApplication;
import csx.haha.com.factory.model.api.account.AccountRspModel;
import csx.haha.com.factory.model.db.User;
import csx.haha.com.factory.model.db.User_Table;

/**
 * Created by sunray on 2018-4-27.
 */

public class Account {
    private static final String TAG = "MainActivity";
    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";
    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    //设备的推送id
    private static String pushId;
    // 设备Id是否已经绑定到了服务器
    private static boolean isBind;
    // 登录状态的Token，用来接口请求
    private static String token;
    // 登录的用户ID
    private static String userId;
    // 登录的账户
    private static String account;

    /**
     * 获取推送id
     * @return
     */
    public static String getPushId() {
        return pushId;
    }

    public static void setPushId(String pushId) {
        Account.pushId = pushId;
        save(MyApplication.getInstance());
    }

    /**
     * 返回当前帐号是否登录
     * @return true表示已登录
     */
    public static boolean isLogin() {
        //用户id和token 不为空
        return !TextUtils.isEmpty(userId)
                && !TextUtils.isEmpty(token);
    }

    /**
     * 是否已经完善了用户信息
     * @return
     */
    public static boolean isComplete() {
        // 首先保证登录成功
        if (isLogin()) {
            User self = getUser();
            Log.d(TAG, "isComplete:desc= " + TextUtils.isEmpty(self.getDesc()) + ",portrait="
                + TextUtils.isEmpty(self.getPortrait()));
            return !TextUtils.isEmpty(self.getDesc())
                    && !TextUtils.isEmpty(self.getPortrait())
                    && self.getSex() != 0;
        }
        // 未登录返回信息不完全
        return false;
    }

    /**
     * 当前帐号是否已经绑定pushId
     * @return true表示已绑定
     */
    public static boolean isBind() {
        return isBind;
    }

    /**
     * 设置绑定状态
     */
    public static void setBind(boolean isBind) {
        Account.isBind = isBind;
        Account.save(MyApplication.getInstance());
    }

    /**
     * 将数据持久化到xml文件中
     * @param context
     */
    private static void save(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName()
            , Context.MODE_PRIVATE);
        sp.edit()
                .putString(KEY_PUSH_ID, pushId)
                .putBoolean(KEY_IS_BIND, isBind)
                .putString(KEY_TOKEN, token)
                .putString(KEY_USER_ID, userId)
                .putString(KEY_ACCOUNT, account)
                .apply();
    }
    /**
     * 到xml文件中读取数据
     * @param context
     */
    public static void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName()
            , Context.MODE_PRIVATE);
        pushId = sp.getString(KEY_PUSH_ID, "");
        isBind = sp.getBoolean(KEY_IS_BIND, false);
        token = sp.getString(KEY_TOKEN, "");
        userId = sp.getString(KEY_USER_ID, "");
        account = sp.getString(KEY_ACCOUNT, "");
    }

    /**
     * 持久化到数据库中
     * @param accountRspModel
     */
    public static void login(AccountRspModel accountRspModel) {
        //存储当前登录的账户，token,用户Id，方便从数据库中查询我的信息
        Account.token = accountRspModel.getToken();
        Account.account = accountRspModel.getAccount();
        Account.userId = accountRspModel.getUser().getId();
        save(MyApplication.getInstance());
    }

    /**
     * 获取当前登录的用户信息
     * @return
     */
    public static User getUser() {
        //如果为null，返回一个new user，其次从数据库查询
        return TextUtils.isEmpty(userId) ? new User() : SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(userId))
                .querySingle();
    }

    /**
     * 返回用户Id
     *
     * @return 用户Id
     */
    public static String getUserId() {
        return getUser().getId();
    }
    /**
     * 获取当前登录的token
     * @return
     */
    public static String getToken() {
        return token;
    }
}
