package com.shixing.setwifitoprint.utils;

import android.util.Log;

import com.shixing.setwifitoprint.MainActivity;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by shixing on 2017/11/1.
 */

public class NetworkSetting {
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    private static final String URL_POST = "http://192.168.223.1/hp/device/config_result_YesNo.html/config";
    private static final String URL_PUT = "http://192.168.223.1/IoMgmt/Adapters/wifi0/Profiles/Active";
    static final MediaType XML = MediaType.parse("application/xml");

    //打开wifi开关
    public static String postData() {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("Clear", "\346\230\257");
        formBodyBuilder.add("Menu", "NetWifiChange");
        formBodyBuilder.add("Configuration", "WifiEnabled=1");
        RequestBody requestBody = formBodyBuilder.build();
        Request request = new Request.Builder()
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
                .addHeader("Cache-Control", "max-age=0")
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Host", "192.168.223.1")
                .addHeader("Origin", "http://192.168.223.1")
                .addHeader("Referer", "http://192.168.223.1/hp/device/config_result_YesNo.html?tab=Networking&amp;menu=NetWifiChange&amp;WifiEnabled=1")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .url(URL_POST)
                .post(requestBody)
                .build();

        try {
            Response response = mOkHttpClient.newCall(request).execute();
            Log.d(MainActivity.TAG, "postData: " + response.code());
            return response.body().string();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    //把用户名和密码以一个xml字符串的形式put到打印机（这里的打印机就是服务器）上
    public static String putMyData() {
        RequestBody requestBody = RequestBody.create(XML,getXml());
        Request request = new Request.Builder().url(URL_PUT)
                .addHeader("Accept","application/xml, text/xml, */*")
                .addHeader("Accept-Encoding","gzip, deflate")
                .addHeader("Connection","keep-alive")
                .addHeader("Accept-Language","zh-CN,zh;q=0.8")
                .addHeader("Host","192.168.223.1")
                .addHeader("Origin","http://192.168.223.1")
                .addHeader("Referer","http://192.168.223.1/set_config_network_Wireless.html?tab=Networking&menu=NetWireless")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .addHeader("X-Requested-With","XMLHttpRequest")
                .put(requestBody).build();

        try {
            Response response = mOkHttpClient.newCall(request).execute();
            Log.d(MainActivity.TAG, "putMyData: " + response.code());
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getXml() {
        return "<?xml version:\"1.0\" encoding=\"UTF-8\" ?><io:Profile  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:dd=\"http://www.hp.com/schemas/imaging/con/dictionaries/1.0/\" xmlns:io=\"http://www.hp.com/schemas/imaging/con/ledm/iomgmt/2008/11/30\" xmlns:wifi=\"http://www.hp.com/schemas/imaging/con/wifi/2009/06/26\" xmlns:ipdyn=\"http://www.hp.com/schemas/imaging/con/ledm/internalprintdyn/2008/03/21\" ><io:AdapterProfile><io:WifiProfile><wifi:SSID>646C696E6B</wifi:SSID> <wifi:CommunicationMode>infrastructure</wifi:CommunicationMode> <wifi:EncryptionType>WPA_PSK</wifi:EncryptionType><wifi:AuthenticationMode>WPA_PSK</wifi:AuthenticationMode><io:KeyInfo><io:WpaPassPhraseInfo><wifi:RsnEncryption>AESOrTKIP</wifi:RsnEncryption><wifi:RsnAuthorization>autoWPA</wifi:RsnAuthorization><wifi:PassPhrase>6E686C323230323230</wifi:PassPhrase></io:WpaPassPhraseInfo></io:KeyInfo></io:WifiProfile></io:AdapterProfile></io:Profile>";
    }
}
