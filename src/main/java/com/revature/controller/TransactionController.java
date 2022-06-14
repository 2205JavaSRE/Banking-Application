package com.revature.controller;
import com.revature.models.*;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;

public class TransactionController {
    public static void postTransaction(Context ctx){
        Transaction t = ctx.bodyAsClass(Transaction.class);
        if(!TransactionService.postTransaction(t)){
            ctx.status(HttpStatus.BAD_REQUEST_400);
        }else{
            ctx.status(HttpStatus.CREATED_201);
        }
    }

    public static void getAllTransactions(Context ctx){
        User u = ctx.sessionAttribute("user");
        if(u.isEmployee()){
            TransactionService.getAllTransactions();
        }else{
            ctx.status(HttpStatus.FORBIDDEN_403);
        }
    }
}
