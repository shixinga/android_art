package com.csx.push;

import com.csx.push.provider.AuthRequestFilter;
import com.csx.push.provider.GsonProvider;
import com.csx.push.service.AccountService;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

/**
 * Created by sunray on 2018-4-16.
 */
public class Application extends ResourceConfig{
    //重启tomcat或者第一次部署到tomcat会调用该方法
    //相当于servlet中的ServletContextListener
    public Application() {

        System.out.print("application");
        //扫描com.csx.push.service包下的类
        packages(AccountService.class.getPackage().getName());

        //注册json解析器(把User转化为json字符串)
//        register(JacksonJsonProvider.class);
        //替换解析器为Gson
        register(GsonProvider.class);

        // 注册我们的全局请求拦截器
        register(AuthRequestFilter.class);

        //注册日志打印
        register(Logger.class);
    }
}
