package com.revature.controller;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.revature.models.*;
import com.revature.service.TransactionService;

import io.javalin.http.Context;
import javalinjwt.JavalinJWT;

import org.eclipse.jetty.http.HttpStatus;

public class TransactionController {
	
	private TransactionController() {}
	
    public static void postTransaction(Context ctx){
        try {
            Transaction t = ctx.bodyAsClass(Transaction.class);
            if (!TransactionService.postTransaction(t, getCurrentUser(ctx))) {
                ctx.status(HttpStatus.BAD_REQUEST_400);
            } else {
                ctx.status(HttpStatus.CREATED_201);
            }
        }catch(Exception e){
            ctx.status(HttpStatus.BAD_REQUEST_400);
        }
    }

    public static void getAllTransactions(Context ctx){
        User u = getCurrentUser(ctx);
        if(u.isEmployee()){
            ctx.json(TransactionService.getAllTransactions());
        }else{
            ctx.status(HttpStatus.FORBIDDEN_403);
        }
    }

	public static void updateTranfer(Context ctx) {
		if(!TransactionService.updateTransfer(ctx.bodyAsClass(Transaction.class), getCurrentUser(ctx))) {
			ctx.status(HttpStatus.FORBIDDEN_403);
		}
	}
	
	//Decodes user info from JWT
	private static User getCurrentUser(Context ctx) {
		DecodedJWT decodedJWT = JavalinJWT.getDecodedFromContext(ctx);
		return new User(decodedJWT.getClaim("userID").asInt(), 
				decodedJWT.getClaim("firstName").asString(), 
				decodedJWT.getClaim("lastName").asString(), 
				decodedJWT.getClaim("username").asString(), 
				decodedJWT.getClaim("password").asString(), 
				decodedJWT.getClaim("isEmployee").asBoolean());
	}
	
}
