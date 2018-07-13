package com.shixing.myhttp.http;

import java.util.Map;

/**
 * Created by shixing on 2018/7/9.
 */

public interface NameValueMap<K,V> extends Map<K, V> {

    String get(String name);

    void set(String name, String value);

    void setAll(Map<String, String> map);

}
