package com.revature.controller;

import com.revature.models.User;
import com.revature.service.AuthenticationService;
import com.revature.service.UserService;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;

public class AuthenticationController {
    public static void authenticate(Context ctx){
        User login = ctx.bodyAsClass(User.class);
        boolean access = AuthenticationService.authenticateUser(login.getUsername(), login.getPassword());
        if(access){
            ctx.status(HttpStatus.OK_200);
            User u = UserService.getUser(login.getUsername());
            ctx.sessionAttribute("user", u);
        }else {
            ctx.status(HttpStatus.UNAUTHORIZED_401);
        }
    }


    public static boolean verifyUser(Context ctx){
        User u = ctx.sessionAttribute("user");
        if(u != null){
            return true;
        }else{
            return false;
        }
    }
}
