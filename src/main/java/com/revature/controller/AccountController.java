package com.revature.controller;

import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;

public class AccountController {
    public static void createAccount(Context ctx){
        Account a = ctx.bodyAsClass(Account.class);
        if(!AccountService.createAccount(a)){
            ctx.status(HttpStatus.BAD_REQUEST_400);
        }else{
            ctx.status(HttpStatus.CREATED_201);
        }
    }

    public static void getAccounts(Context ctx){
        User u = ctx.sessionAttribute("user");
        if(u.getIsEmployee()){
            ctx.json(AccountService.getAllAccounts());
        }else{
            ctx.json(AccountService.getAccountsByUsername(u.getUsername()));
        }
    }


}
