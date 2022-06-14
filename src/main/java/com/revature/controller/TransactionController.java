package com.revature.controller;
import com.revature.models.*;
import com.revature.service.TransactionService;

import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;

public class TransactionController {
    public static void postTransaction(Context ctx){
        try {
            Transaction t = ctx.bodyAsClass(Transaction.class);
            if (!TransactionService.postTransaction(t, ctx.sessionAttribute("user"))) {
                ctx.status(HttpStatus.BAD_REQUEST_400);
            } else {
                ctx.status(HttpStatus.CREATED_201);
            }
        }catch(Exception e){
            ctx.status(HttpStatus.BAD_REQUEST_400);
        }
    }

    public static void getAllTransactions(Context ctx){
        User u = ctx.sessionAttribute("user");
/*        if(u.isEmployee()){
            TransactionService.getAllTransactions();
        }else{
            ctx.status(HttpStatus.FORBIDDEN_403);
        }*/
    }
}
