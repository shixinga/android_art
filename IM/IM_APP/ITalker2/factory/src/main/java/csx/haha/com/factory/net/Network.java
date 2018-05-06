package csx.haha.com.factory.net;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import csx.haha.com.common.Common;
import csx.haha.com.factory.Factory;
import csx.haha.com.factory.persistence.Account;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sunray on 2018-4-26.
 */

public class Network {
    private static final String TAG = "MainActivity";
    private static Network sInstance;
    private Retrofit mRetrofit;

    static {
        sInstance = new Network();
    }

    private Network() {

    }
    //构建一个Retrofit
    public static Retrofit getRetrofit() {
        if (sInstance.mRetrofit != null) {
            return sInstance.mRetrofit;
        }
        OkHttpClient client = new OkHttpClient.Builder()
                //给所有的请求添加一个拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拿到我们的请求
                        Request original = chain.request();
                        //重新进行build
                        Request.Builder builder = original.newBuilder();
                        if (!TextUtils.isEmpty(Account.getToken())) {
                            //注入一个token
                            builder.addHeader("token" , Account.getToken());
                        }
                        //content-type要加，因为Retrofit默认不帮我们加！！！
                        //但是如果传递的数据不需要放在消息体里面，也可以不加
//                        builder.addHeader("Content-Type", "application/json");
                        Request newRequest = builder.build();
Log.d(TAG, "intercept: token=" + Account.getToken());
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();

        //设置电脑连接
        sInstance.mRetrofit = builder.baseUrl(Common.Constance.API_URL)
                        //设置client
                       .client(client)
                        //设置json解析器
                        .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                        .build();
        return sInstance.mRetrofit;
    }

    /**
     * 返回一个请求代理
     *
     * @return RemoteService
     */
    public static RemoteService remote() {
        return Network.getRetrofit().create(RemoteService.class);
    }

}
