package com.shixing.myhttp.utils;

/**
 * Created by shixing on 2018/7/9.
 */

public class Utills {

    public static boolean isClassExist(String className, ClassLoader loader) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
