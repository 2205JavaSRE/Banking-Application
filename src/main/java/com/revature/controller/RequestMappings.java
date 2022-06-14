package com.revature.controller;
import com.revature.util.Monitor;
import io.javalin.Javalin;
import org.eclipse.jetty.http.HttpStatus;

public class RequestMappings {
    public static void configureRoutes(Javalin app){
        //General login paths
        app.post("/api/v1/login", ctx -> {
           AuthenticationController.authenticate(ctx);
        });

        app.post("/api/v1/logout", ctx -> {
            ctx.consumeSessionAttribute("user");
            ctx.status(HttpStatus.OK_200);
        });
        //Customer post requests
        app.post("/api/v1/register", ctx -> {
           AuthenticationController.createUser(ctx);
        });

        app.post("/api/v1/account", ctx -> {
            if(AuthenticationController.verifyUser(ctx)){
                AccountController.createAccount(ctx);
            }else{
                ctx.status(HttpStatus.UNAUTHORIZED_401);
            }
        });

        app.post("/api/v1/transactions", ctx -> {
           if(AuthenticationController.verifyUser(ctx)){
               TransactionController.postTransaction(ctx);
           }else{
               ctx.status(HttpStatus.UNAUTHORIZED_401);
           }
        });
        //Dual-purpose get requests
        app.get("/api/v1/accounts", ctx -> {
           if(AuthenticationController.verifyUser(ctx)){
               AccountController.getAccounts(ctx);
           }else{
               ctx.status(HttpStatus.UNAUTHORIZED_401);
           }
        });

        app.get("/api/v1/transactions", ctx -> {
           if(AuthenticationController.verifyUser(ctx)){
               TransactionController.getAllTransactions(ctx);
           }else{
               ctx.status(HttpStatus.UNAUTHORIZED_401);
           }
        });

        app.get("/api/v1/accounts/{id}", ctx -> {
            if(AuthenticationController.verifyUser(ctx)){
                AccountController.getAccountsById(ctx);
            }else{
                ctx.status(HttpStatus.UNAUTHORIZED_401);
            }
        });



    }

}
