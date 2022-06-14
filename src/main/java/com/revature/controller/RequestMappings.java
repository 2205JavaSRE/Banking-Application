package com.revature.controller;
import com.revature.util.Monitor;
import io.javalin.Javalin;
import org.eclipse.jetty.http.HttpStatus;

public class RequestMappings {
    public static void configureRoutes(Javalin app){
        app.post("/api/v1/login", ctx -> {
           AuthenticationController.authenticate(ctx);
        });

        app.post("/api/v1/logout", ctx -> {
            ctx.consumeSessionAttribute("user");
            ctx.status(HttpStatus.OK_200);
        });

        app.post("/api/v1/register", ctx -> {
           AuthenticationController.createUser(ctx);
        });

        app.post("/api/v1/account/apply", ctx -> {
            if(AuthenticationController.verifyUser(ctx)){
                AccountController.createAccount(ctx);
            }else{
                ctx.status(HttpStatus.UNAUTHORIZED_401);
            }
        });

        app.post("/api/v1/transaction", ctx -> {
           if(AuthenticationController.verifyUser(ctx)){
               TransactionController.postTransaction(ctx);
           }else{
               ctx.status(HttpStatus.UNAUTHORIZED_401);
           }

        });



    }

}
