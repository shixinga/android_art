package one.com.serializable.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by shixing on 2017/4/24.
 */

public class MyUtils {
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
