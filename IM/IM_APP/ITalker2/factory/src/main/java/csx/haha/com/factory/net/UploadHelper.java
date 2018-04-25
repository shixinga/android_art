package csx.haha.com.factory.net;

import android.text.format.DateFormat;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.util.Date;

import csx.haha.com.common.app.MyApplication;
import csx.haha.com.utils.HashUtil;

/**
 * 上传工具类，用于上传文件到阿里OSS存储
 * Created by sunray on 2018-4-25.
 *
 */

public class UploadHelper {
    private static final String TAG = "MAinActivity";
    private static final String  ENDPOINT =  "oss-cn-shenzhen.aliyuncs.com";
    // 上传的仓库名
    private static final String BUCKET_NAME = "csx0405";


    private static OSS getClient() {
        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(
                "LTAIz1nLi7nT4Ryk", "ZiaMwnXDm2TFpUV3C1x41C29auozjZ");
        return new OSSClient(MyApplication.getInstance(), ENDPOINT, credentialProvider);
    }

    /**
     * 上传的最终方法，成功则返回一个路径
     * @param objKey 上传到服务器后，在服务器上的独立的KEY
     * @param path 需要上传的文件的路径
     * @return 存储的地址
     */
    private static String upload(String objKey, String path) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, objKey, path);

        try {
            OSS client = getClient();
            //开始同步上传
            PutObjectResult result = client.putObject(putObjectRequest);
            //得到一个外网可访问的地址
            String url = client.presignPublicObjectURL(BUCKET_NAME, objKey);
            Log.d(TAG, String.format("PublicObjectURL:%s , objKey:%s", url, objKey));
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 上传普通图片
     *
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadImage(String path) {
        String key = getImageObjKey(path);
        return upload(key, path);
    }

    /**
     * 上传头像
     *
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadPortrait(String path) {
        String key = getPortraitObjKey(path);
        return upload(key, path);
    }

    /**
     * 上传音频
     *
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadAudio(String path) {
        String key = getAudioObjKey(path);
        return upload(key, path);
    }

    /**
     * 分月存储，避免一个文件夹太多
     *
     * @return yyyyMM
     */
    private static String getDateString() {
        return DateFormat.format("yyyyMM", new Date()).toString();
    }

    // image/201703/dawewqfas243rfawr234.jpg
    private static String getImageObjKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("image/%s/%s.jpg", dateString, fileMd5);
    }

    // portrait/201703/dawewqfas243rfawr234.jpg
    private static String getPortraitObjKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("portrait/%s/%s.jpg", dateString, fileMd5);
    }

    // audio/201703/dawewqfas243rfawr234.mp3
    private static String getAudioObjKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("audio/%s/%s.mp3", dateString, fileMd5);
    }
}
