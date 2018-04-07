package com.shixing.myandfix;

import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.IOException;

/**
 * Created by shixing on 2018/4/5.
 */

public class AndFixManager {
    private static AndFixManager sInstance = null;
    private PatchManager mPatchManager = null;
    private AndFixManager() {}

    public static AndFixManager getInstance() {
        if (sInstance == null) {
            synchronized (AndFixManager.class) {
                if (sInstance == null) {
                    sInstance = new AndFixManager();
                }
            }
        }

        return sInstance;
    }

    public void init(Context context) {
        mPatchManager = new PatchManager(context);
        mPatchManager.init(Utils.getVersionName(context));
        mPatchManager.loadPatch();
    }

    public void addPatch(String path) {
        try {
            if (mPatchManager != null) {
                mPatchManager.addPatch(path);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
