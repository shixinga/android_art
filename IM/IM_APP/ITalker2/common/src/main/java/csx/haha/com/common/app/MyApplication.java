package csx.haha.com.common.app;

import android.app.Application;
import android.os.SystemClock;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.io.File;

/**
 * Created by sunray on 2018-4-24.
 */

public class MyApplication extends Application {
    private static final String TAG = "MainActivity";
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    /**
     * 用处：图片剪切时保存的临时图片文件
     * 获取缓存文件夹的地址
     * @return 当前APP的缓存文件夹地址
     */
    public static File getCacheDirFile() {
        return sInstance.getCacheDir();
    }

    public static File getPortraitTmpFile() {

        //得到头像目录的缓存地址
        File dir = new File(sInstance.getCacheDir(), "portrait");
        dir.mkdirs();

        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for(File file : files) {
                file.delete();
            }
        }
        File path = new File(dir, SystemClock.uptimeMillis() + ".jpg");
//Log.d(TAG, "getPortraitTmpFile: " + path.getAbsolutePath());
        return path.getAbsoluteFile();

    }

    /**
     * 获取声音文件的本地地址
     *
     * @param isTmp 是否是缓存文件， True，每次返回的文件地址是一样的
     * @return 录音文件的地址
     */
    public static File getAudioTmpFile(boolean isTmp) {
        File dir = new File(getCacheDirFile(), "audio");
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }

        // aar
        File path = new File(getCacheDirFile(), isTmp ? "tmp.mp3" : SystemClock.uptimeMillis() + ".mp3");
        return path.getAbsoluteFile();
    }

    /**
     * 显示一个Toast
     *
     * @param msg 字符串
     */
    public static void showToast(final String msg) {
//        Toast.makeText(sInstance, msg, Toast.LENGTH_SHORT).show();
        // Toast 只能在主线程中显示，所有需要进行线程转换，
        // 保证一定是在主线程进行的show操作
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                // 这里进行回调的时候一定就是主线程状态了
                Toast.makeText(sInstance, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 显示一个Toast
     *
     * @param msgId 传递的是字符串的资源
     */
    public static void showToast(@StringRes int msgId) {
        showToast(sInstance.getString(msgId));
    }

}
