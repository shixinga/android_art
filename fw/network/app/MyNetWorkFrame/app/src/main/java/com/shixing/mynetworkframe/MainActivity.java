package com.shixing.mynetworkframe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.shixing.filedownload.DownloadManager;
import com.shixing.filedownload.file.FileStorageManager;
import com.shixing.filedownload.http.DownloadCallback;
import com.shixing.filedownload.http.HttpManager;
import com.shixing.filedownload.utils.Logger;
import com.shixing.myhttp.service.MyAbstractResponse;
import com.shixing.myhttp.service.MyApiProvider;
import com.shixing.myhttp.service.MyRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView mIv;
    ProgressBar mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //单线程下载文件
//        mIv = (ImageView) findViewById(R.id.iv);
       /* HttpManager.getInstance().asyncRequest("http://i1.umei.cc/uploads/tu/201609/324/esruzvurpox.jpg", new DownloadCallback() {
            @Override
            public void success(final File file) {
                Logger.debug("main", file.getAbsolutePath());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        mIv.setImageBitmap(bitmap);
                    }
                });

            }

            @Override
            public void fail(int errorCode, String errorMessage) {
                Logger.debug("main",errorCode + "," + errorMessage);
            }

            @Override
            public void progress(int progress) {

            }
        });*/

        //多线程下载一个文件
       /* mProgress = (ProgressBar) findViewById(R.id.pb);
        File file = FileStorageManager.getInstance().getFileByName("https://shouji.ssl.qihucdn.com/180621/2130d77233a2da058d8c18d600a5c5ce/com.qihoo.appstore_300070190.apk");
        Logger.debug("nate", "file path = " + file.getAbsoluteFile());
        final String url = "http://ws.yingyonghui.com/5f7fb97d603bb0e9412acafb39ba3c83/5b42b560/apk/5951696/e0c431e9e3e2f0b0558f013cabbbc199";
        DownloadManager.getInstance().download(url, new DownloadCallback() {
            //坑1：要等最后一个线程到来时（即文件下载完毕）才能开启安装apk
            //坑2：最后一个线程现在的文件范围的结束位置不是(i + 1) * 每个线程的划分范围  - 1,而是文件的总长度 - 1
            int count = 1;
            @Override
            public void success(File file) {
                if (count < DownloadManager.MAX_THREAD) {
                    count++;
                    return;
                }
                installApk(file);
                Logger.debug("main", "success " + file.getAbsoluteFile());

            }

            @Override
            public void fail(int errorCode, String errorMessage) {
                Logger.debug("main", "fail " + errorCode + "  " + errorMessage);
            }

            @Override
            public void progress(int progress) {
                Logger.debug("main", "progress    " + progress);
                mProgress.setProgress(progress);

            }
        });*/



       //测试 将返回的json字符串转化为相应的对象
        Map<String, String> map = new HashMap<>();
        map.put("username", "nate");
        map.put("age", "12");
        MyApiProvider.helloWorld("http://192.168.1.205:8080/NetworkServer/helloServlet", map, new MyAbstractResponse<Person>() {
            @Override
            public void success(MyRequest request, Person data) {
                Logger.debug("main", data.toString());
            }
            @Override
            public void fail(int errorCode, String errorMsg) {
            }
        });

    }

    private void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + file.getAbsoluteFile().toString()), "application/vnd.android.package-archive");
        MainActivity.this.startActivity(intent);
    }
}
