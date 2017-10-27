package one.com.serializable.utils;

import android.os.Environment;

/**
 * Created by shixing on 2017/4/24.
 */

public class MyConstants {
    public static String dirPath =  Environment
            .getExternalStorageDirectory().getPath() + "/csx/your/";
    public static String CACHE_FILE_PATH = dirPath + "yourcache";
}
