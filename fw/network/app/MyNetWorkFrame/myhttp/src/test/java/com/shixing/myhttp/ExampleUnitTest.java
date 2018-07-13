package com.shixing.myhttp;

import com.shixing.myhttp.api.HttpRequestProvider;
import com.shixing.myhttp.api.OkHttpRequestFactory;
import com.shixing.myhttp.api.OriginHttpRequestFactory;
import com.shixing.myhttp.http.HttpMethod;
import com.shixing.myhttp.http.HttpRequest;
import com.shixing.myhttp.http.HttpResponse;
import com.shixing.myhttp.requestandresponse.myokhttp.MyOkhttpRequest;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import okhttp3.OkHttpClient;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        //post请求测试
        /*OkHttpClient client = new OkHttpClient();
        MyOkhttpRequest myOkhttpRequest = new MyOkhttpRequest(client, HttpMethod.POST, "http://localhost:8080/NetworkServer/testMyOkhttpRequestServlet");
        myOkhttpRequest.getBody().write("username=csxaa&age=2222".getBytes());
        HttpResponse myOkHttpResponse = myOkhttpRequest.execute();
        String content = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myOkHttpResponse.getBody()));
        while ((content = bufferedReader.readLine()) != null) {
            System.out.println(content);
        }
        myOkHttpResponse.close();*/



        //get请求测试
/*
        OkHttpClient client = new OkHttpClient();
        MyOkhttpRequest myOkhttpRequest = new MyOkhttpRequest(client, HttpMethod.GET, "http://www.baidu.com");
        HttpResponse myOkHttpResponse = myOkhttpRequest.execute();
        String content = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myOkHttpResponse.getBody()));
        while ((content = bufferedReader.readLine()) != null) {
            System.out.println(content);
        }
        myOkHttpResponse.close();
*/
    }

    //测试封装后的okhttp接口
    @Test
    public void testMyOkhttpApi() {
        HttpRequestProvider provider = new HttpRequestProvider();
        OkHttpRequestFactory okHttpRequestFactory = (OkHttpRequestFactory) provider.getHttpRequestFactory();
        try {
            HttpRequest request = okHttpRequestFactory.createHttpRequest(URI.create("http://www.baidu.com"), HttpMethod.GET);
            HttpResponse myOkHttpResponse = request.execute();
            String content = null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myOkHttpResponse.getBody()));
            while ((content = bufferedReader.readLine()) != null) {
                System.out.println(content);
            }
            myOkHttpResponse.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //测试封装后的原始网络（HttpUrlConnection）接口
    @Test
    public void testMyOriginalNetworkApi() {
//        HttpRequestProvider provider = new HttpRequestProvider();
        OriginHttpRequestFactory factory = new OriginHttpRequestFactory();
        try {
            HttpRequest request = factory.createHttpRequest(URI.create("http://www.baidu.com"), HttpMethod.GET);
            HttpResponse myResponse = request.execute();
            String content = null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myResponse.getBody()));
            while ((content = bufferedReader.readLine()) != null) {
                System.out.println(content);
            }
            myResponse.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}