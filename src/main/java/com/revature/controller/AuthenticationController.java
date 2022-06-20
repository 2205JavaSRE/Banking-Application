package com.revature.controller;

import com.revature.models.User;
import com.revature.service.AuthenticationService;
import com.revature.service.UserService;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;

public class AuthenticationController {
	
	private AuthenticationController() {}
	
    public static void authenticate(Context ctx){
        User login = ctx.bodyAsClass(User.class);
        boolean access = AuthenticationService.authenticate(login.getUsername(), login.getPassword());
        if(access){
            ctx.status(HttpStatus.OK_200);
            User u = UserService.getUser(login.getUsername());
            ctx.cookieStore("username", u.getUsername());
            ctx.cookieStore("auth", u.getPassword());
            ctx.cookieStore("userID", u.getUserID());
            ctx.cookieStore("firstName", u.getFirstName());
            ctx.cookieStore("lastName", u.getLastName());
            ctx.cookieStore("employee", u.isEmployee());
        }else {
            ctx.status(HttpStatus.UNAUTHORIZED_401);
            System.out.println("AuthenticationController.authenticate: false");
        }
    }


    public static boolean verifyUser(Context ctx){
        User u = retrieveUserFromCookie(ctx);
        if(u != null){
            return true;
        }else{
            return false;
        }
    }
    
    public static void createUser(Context ctx) {
    	try {
    		User u = ctx.bodyAsClass(User.class);
        	if (!UserService.createUser(u)) {
        		ctx.status(HttpStatus.BAD_REQUEST_400);
        	}else{
                ctx.status(HttpStatus.CREATED_201);
            }
    	} catch (Exception e) {
    		ctx.status(HttpStatus.BAD_REQUEST_400);
    	}
    }

    public static User retrieveUserFromCookie(Context ctx){
        User u = null;
        String username = ctx.cookieStore("username");
        String pass = ctx.cookieStore("auth");
        Integer userID = ctx.cookieStore("userID");
        String fName = ctx.cookieStore("fName");
        String lName = ctx.cookieStore("lName");
        Boolean isEmployee = ctx.cookieStore("employee");
        if(username != null){
            u = new User(userID, fName, lName, username, pass, isEmployee);
        }

        return u;
    }
    
}
