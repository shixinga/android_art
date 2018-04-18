package com.csx.push.service;

import com.csx.push.bean.User;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by sunray on 2018-4-17.
 */
//访问路径： http://localhost:8080/api/account/login
// url不用project name！！
@Path("/account")
public class AccountService {

    @GET
    @Path("/login")
//    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        System.out.print("AccountService");
        return "you get";
    }

    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public User post() {
        User user = new User();
        user.setName("csxxx");
        user.setSex(1);
        return user;
    }
}
