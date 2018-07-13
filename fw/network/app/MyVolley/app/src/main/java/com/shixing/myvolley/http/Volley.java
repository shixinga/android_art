package com.shixing.myvolley.http;


import com.shixing.myvolley.http.interfaces.IDataListener;
import com.shixing.myvolley.http.interfaces.IHttpListener;
import com.shixing.myvolley.http.interfaces.IHttpService;

import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class Volley {
    /**
     *
     * @param <T>  请求参数类型
     * @param <M>  响应参数类型
     *           暴露给调用层
     */
    public static <T,M> void sendRequest(T  requestInfo, String url,
                                         Class<M> response, IDataListener dataListener)
    {
        RequestHodler<T> requestHodler=new RequestHodler<>();
        requestHodler.setUrl(url);
        IHttpService httpService=new JsonHttpService();
        IHttpListener httpListener=new JsonDealLisener<>(response,dataListener);
        requestHodler.setHttpService(httpService);
        requestHodler.setHttpListener(httpListener);
        requestHodler.setRequestInfo(requestInfo



        );
        HttpTask<T> httpTask=new HttpTask<>(requestHodler);
        try {
            ThreadPoolManager.getsInstance().execte(new FutureTask<Object>(httpTask,null));
        } catch (InterruptedException e) {
            e.printStackTrace();
            dataListener.onFail();
        }
    }

}
