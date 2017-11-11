package com.shixing.q16setwifi.utils;

/**
 * Created by shixing on 2017/11/11.
 */

public class StringToHex {
    public static String stringToHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
}
