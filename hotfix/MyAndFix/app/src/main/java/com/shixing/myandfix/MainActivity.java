package com.shixing.myandfix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

/**
 * getPAtchName() 返回的是 /storage/emulated/0/Android/data/com.shixing.myandfix/cache/apatch/ni.apatch
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    String mPatchDir;
    private static final String FILE_SUF = ".apatch";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPatchDir = getExternalCacheDir().getAbsolutePath() + "/apatch/";
        File dir = new File(mPatchDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    public void createBug(View view) {
//        String error = null;
        String error = "哥修正了bug,look!!!";
        Log.e(TAG, error); //nullPointerException
    }
    public void fixBug(View view) {
        AndFixManager.getInstance().addPatch(getPAtchName());
    }

    private String getPAtchName() {
        return mPatchDir.concat("ni").concat(FILE_SUF);
    }
}
