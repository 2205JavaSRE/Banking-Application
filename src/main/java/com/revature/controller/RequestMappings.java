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


    }

}
