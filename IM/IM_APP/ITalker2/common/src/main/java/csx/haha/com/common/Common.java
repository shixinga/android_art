package csx.haha.com.common;

/**
 * 服务器的ip在这里设置
 * 二图片的上传和下载在UploadHelper.java中设置
 * Created by sunray on 2018-4-17.
 */

public class Common {
    /**
     * 一些不可变的永恒的参数
     * 通常用于一些配置
     * 个推的参数在app的build.gradle里面设置
     */
    public interface Constance {
        // 手机号的正则,11位手机号
        String REGEX_MOBILE = "[1][3,4,5,7,8][0-9]{9}$";

        // 基础的网络请求地址
        String API_URL = "http://47.106.38.102:8080/iTalker/api/";
//        String API_URL = "http://ip address/iTalker/api/";

        // 最大的上传图片大小860kb
        long MAX_UPLOAD_IMAGE_LENGTH = 860 * 1024;
    }
}
