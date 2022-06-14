package com.revature.controller;

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
}
