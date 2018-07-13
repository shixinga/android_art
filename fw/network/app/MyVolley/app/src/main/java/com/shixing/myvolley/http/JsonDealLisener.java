package com.shixing.myvolley.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.shixing.myvolley.http.interfaces.IDataListener;
import com.shixing.myvolley.http.interfaces.IHttpListener;

import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class JsonDealLisener<M> implements IHttpListener {
    private static final String TAG = "Main";
    private Class<M> responceClass;
    private IDataListener<M> dataListener;
    /**
     * 获取主线程的Handle
     * 通过handle切换至主线程
     */
    Handler handler=new Handler(Looper.getMainLooper());

    public JsonDealLisener(Class<M> responceClass, IDataListener<M> dataListener) {
        this.responceClass = responceClass;
        this.dataListener = dataListener;
    }

    @Override
    public void onSuccess(HttpEntity httpEntity) {
        InputStream inputStream=null;
        try {
            inputStream=httpEntity.getContent();
            String content=getContent(inputStream);
            final M responce= JSON.parseObject(content,responceClass);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dataListener.onSuccess(responce);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            dataListener.onFail();
        }

    }

    @Override
    public void onFail() {
        dataListener.onFail();
    }


    private String getContent(InputStream inputStream) {
        String content=null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder();

            String line = null;

            try {

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

            } catch (IOException e) {

                System.out.println("Error=" + e.toString());
                dataListener.onFail();
            } finally {

                try {

                    inputStream.close();

                } catch (IOException e) {

                    System.out.println("Error=" + e.toString());

                }

            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            dataListener.onFail();
        }
        return content;
    }
}
