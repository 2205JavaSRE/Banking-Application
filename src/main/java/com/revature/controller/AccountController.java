package com.revature.controller;
import com.revature.models.*;
import com.revature.service.AccountService;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;

public class AccountController {
    public static void createAccount(Context ctx){
        User u = ctx.sessionAttribute("user");
        
        try {
        	Account a = ctx.bodyAsClass(Account.class);
        	
        	if(!AccountService.createAccount(a, u)){
                ctx.status(HttpStatus.BAD_REQUEST_400);
            }else{
                ctx.status(HttpStatus.CREATED_201);
            }
        } catch (Exception e) {
			ctx.status(HttpStatus.BAD_REQUEST_400);
		}  
    }

    public static void getAccounts(Context ctx){
        User u = ctx.sessionAttribute("user");
        if(u.isEmployee()){
            ctx.json(AccountService.getAllPendingAccounts());
        }else{
            ctx.json(AccountService.getAccountsByUserId(u.getUserID()));
        }
    }

    public static void getAccountsById(Context ctx){
        User u = ctx.sessionAttribute("user");
        if(u.isEmployee()){
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                ctx.json(AccountService.getAccountsByUserId(id));
            }catch(Exception e){
                ctx.status(HttpStatus.BAD_REQUEST_400);
            }
        }else{
            ctx.status(HttpStatus.FORBIDDEN_403);
        }
    }

	public static void updateAccountStatus(Context ctx) {
		try {
			Account a = ctx.bodyAsClass(Account.class);
			AccountService.updateAccountStatus(a);
		} catch (Exception e) {
			ctx.status(HttpStatus.BAD_REQUEST_400);
		}
	}
	
	

}
