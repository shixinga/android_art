package csx.haha.com.factory.data.helper;

import android.text.TextUtils;

import csx.haha.com.factory.Factory;
import csx.haha.com.factory.data.DataSource;
import csx.haha.com.factory.model.api.RspModel;
import csx.haha.com.factory.model.api.account.AccountRspModel;
import csx.haha.com.factory.model.api.account.LoginModel;
import csx.haha.com.factory.model.api.account.RegisterModel;
import csx.haha.com.factory.model.db.User;
import csx.haha.com.factory.net.Network;
import csx.haha.com.factory.net.RemoteService;
import csx.haha.com.factory.persistence.Account;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sunray on 2018-4-26.
 */

public class AccountHelper {
    private static final String TAG = "MainActivity";
    /**
     * 注册的接口，异步调用
     * @param model 传递一个注册的Model进来
     * @param callback 注册结果的回调
     */
    public static void register(RegisterModel model, final DataSource.Callback<User> callback) {
        //调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.getRetrofit().create(RemoteService.class);
        //得到一个Call
        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);

        //异步请求
        call.enqueue(new Callback<RspModel<AccountRspModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
                //请求成功
                //从返回中得到全局的Model，内部是使用Gson解析
                RspModel<AccountRspModel> rspModel = response.body();
                if (rspModel.success()) {
                    //拿到实体
                    AccountRspModel accountRspModel = rspModel.getResult();
                    //判断绑定状态，是否绑定设备
                    if (accountRspModel.isBind()) {
                        User user = accountRspModel.getUser();
                        //进行的是数据库的写入和缓存绑定
                        //然后返回
                        callback.onDataLoaded(user);
                    } else { //我要绑定设备
                        bindPushId(callback);
                    }
                } else {
                    //打印错误
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
                //网络请求失败
                callback.onDataNotAvailable("网络有问题");
            }
        });
    }

    /**
     * 登录的调用
     *
     * @param model    登录的Model
     * @param callback 成功与失败的接口回送
     */
    public static void login(final LoginModel model, final DataSource.Callback<User> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        // 得到一个Call
        Call<RspModel<AccountRspModel>> call = service.accountLogin(model);
        // 异步的请求
        call.enqueue(new AccountRspCallback(callback));
    }

    /**
     * 对设备进行绑定的操作
     * @param callback
     */
    public static void bindPushId(final DataSource.Callback<User> callback) {
//        callback.onDataNotAvailable("我要绑定push id:" + Account.getPushId());

        // 检查是否为空
        String pushId = Account.getPushId();
        if (TextUtils.isEmpty(pushId))
            return;

        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        Call<RspModel<AccountRspModel>> call = service.accountBind(pushId);
        call.enqueue(new AccountRspCallback(callback));
    }


    /**
     * 请求的回调部分封装
     */
    private static class AccountRspCallback implements Callback<RspModel<AccountRspModel>> {

        final DataSource.Callback<User> callback;

        AccountRspCallback(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call,
                               Response<RspModel<AccountRspModel>> response) {
            // 请求成功返回
            // 从返回中得到我们的全局Model，内部是使用的Gson进行解析
            RspModel<AccountRspModel> rspModel = response.body();
            if (rspModel.success()) {
                // 拿到实体
                AccountRspModel accountRspModel = rspModel.getResult();
                // 获取我的信息
                User user = accountRspModel.getUser();
                DbHelper.save(User.class, user);

                // 第一种，之间保存
//                 user.save();
//                /*
//                // 第二种通过ModelAdapter
//                FlowManager.getModelAdapter(User.class)
//                        .save(user);
//
//                // 第三种，事务中
//                DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
//                definition.beginTransactionAsync(new ITransaction() {
//                    @Override
//                    public void execute(DatabaseWrapper databaseWrapper) {
//                        FlowManager.getModelAdapter(User.class)
//                                .save(user);
//                    }
//                }).build().execute();
//                */
                // 同步到XML持久化中
                Account.login(accountRspModel);

                // 判断绑定状态，是否绑定设备
                if (accountRspModel.isBind()) {
                    // 设置绑定状态为True
                    Account.setBind(true);
                    // 然后返回
                    if (callback != null)
                        callback.onDataLoaded(user);
                } else {
                    // 进行绑定的唤起
                    bindPushId(callback);
                }
            } else {
                // 错误解析
                Factory.decodeRspCode(rspModel, callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            // 网络请求失败
            if (callback != null)
                callback.onDataNotAvailable("网络错误");
        }
    }

}
