package csx.haha.com.optimization.s3;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import csx.haha.com.optimization.R;

/**
 * Created by sunray on 2018-3-17.
 */

public class S3WaitLockActivity extends Activity {
    private static final String TAG = "MainActivity";
    TextView mTv;
    PowerManager.WakeLock mWakeLock;
    PowerManager mPw;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s3_waitlock);
        mTv = (TextView) findViewById(R.id.tv_s3_waitlock);
        mPw = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = mPw.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"mywakelocka");
    }

    public void click1(View view) {
        mTv.setText("下载下载");
        for (int i = 0; i < 10; ++i) {
            mWakeLock.acquire(); //唤醒CPU
            mTv.append("连接中....");
            //下载
            if (isNetworkConnect()) {
                new DownloadTask().execute();
            } else {
                Toast.makeText(this,"没有网络",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isNetworkConnect() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return (ni != null && ni.isConnected());
    }

    private class DownloadTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                // Only display the first 50 characters of the retrieved web page content.
                int len = 50;

                URL url = new URL("https://www.baidu.com");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000); // 10 seconds
                conn.setConnectTimeout(15000); // 15 seconds
                conn.setRequestMethod("GET");
                //Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.d(TAG, "The response is: " + response);
                InputStream is = conn.getInputStream();

                // Convert the input stream to a string
                Reader reader = new InputStreamReader(is, "UTF-8");
                char[] buffer = new char[len];
                reader.read(buffer);
                return new String(buffer);

            } catch (IOException e) {
                return "Unable to retrieve web page.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            mTv.append("\n" + result + "\n");
            releaseWakeLock();
        }
    }

    private void releaseWakeLock(){
        if(mWakeLock.isHeld()){
            mWakeLock.release();//记得释放CPU锁
            mTv.append("释放锁！");
        }
    }

}
