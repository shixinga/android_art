package one.com.serializable;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import one.com.serializable.utils.MyConstants;
import one.com.serializable.utils.MyUtils;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        persistFile();
    }

    public void persistFile() {
        new Thread(new Runnable() {

            @Override
            public void run() {
               User user = new User(1, "hello world", true);
               File dir = new File(MyConstants.dirPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                    Log.d(TAG,dir.getAbsolutePath() + "write file!!!");
                }
                File cacheFile = new File(MyConstants.CACHE_FILE_PATH);
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(new FileOutputStream(cacheFile));
                    oos.writeObject(user);

                    Log.d(TAG,"write:" + user.id + ".." + user.name + "..isMale=" + user.isMale );

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    MyUtils.close(oos);
                }
            }
        }).start();
    }

    public void readFromFileOnClick(View view) {
        new Thread(){
            @Override
            public void run() {
                File cacheFile = new File(MyConstants.CACHE_FILE_PATH);
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(new FileInputStream(cacheFile));
                    User user2 = (User) ois.readObject();
                    Log.d(TAG,"read:" + user2.id + ".." + user2.name + "..isMale=" + user2.isMale);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    MyUtils.close(ois);

                }
            }
        }.start();
    }
}
