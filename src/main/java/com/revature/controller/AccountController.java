package com.revature.controller;
import com.revature.models.*;
import com.revature.service.AccountService;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import java.util.*;
public class AccountController {
    public static void createAccount(Context ctx){
        User u = ctx.sessionAttribute("user");
        System.out.println(u);
        try {
        	Account a = ctx.bodyAsClass(Account.class);
            System.out.println(a);
        	if(!AccountService.createAccount(a, u)){
                System.out.println("inside the if!");
                ctx.status(HttpStatus.BAD_REQUEST_400);
            }else{
                ctx.status(HttpStatus.CREATED_201);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                List<Account> aList = AccountService.getAccountsByUserId(id);
                //TODO reevaluate this code
                if(aList.isEmpty()){
                    ctx.status(HttpStatus.NOT_FOUND_404);
                }else{
                    ctx.json(aList);
                }

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
