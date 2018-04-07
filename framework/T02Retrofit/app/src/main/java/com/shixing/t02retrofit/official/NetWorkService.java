package com.shixing.t02retrofit.official;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetWorkService {
  
    /** 
     * 一个get请求的请求接口,返回是字符串 
     * 
     * @return 
     * 
     */  
    @GET("50464873")
    public Call<String> get();
  
}  