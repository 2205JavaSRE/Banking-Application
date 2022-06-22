package com.revature.controller;

import com.revature.models.User;
import com.revature.util.Monitor;
import io.javalin.Javalin;

import org.eclipse.jetty.http.HttpStatus;

public class RequestMappings {

	private RequestMappings() {}

    public static void configureRoutes(Javalin app, Monitor monitor){

    	//Needed for JWT
    	app.before(AuthenticationController.decodeHandler);

        //General login paths
        app.post("/api/v1/login", ctx -> {
            monitor.getRequestLatency().record(() -> {
                AuthenticationController.authenticate(ctx);
            });
        });

        app.post("/api/v1/logout", ctx -> {
            ctx.removeCookie("jwt");
            ctx.status(HttpStatus.OK_200);
        });

        //Customer post requests
        app.post("/api/v1/register", ctx ->{
            monitor.getRequestLatency().record(() -> {
                AuthenticationController.createUser(ctx);
            });
        });

        app.post("/api/v1/accounts", ctx -> {
                    monitor.getRequestLatency().record(() -> {
                        if (AuthenticationController.verifyUser(ctx)) {
                            AccountController.createAccount(ctx);
                        } else {
                            ctx.status(HttpStatus.UNAUTHORIZED_401);
                        }
                    });
                });

        app.post("/api/v1/transactions", ctx -> {
            monitor.getRequestLatency().record(() -> {
                if (AuthenticationController.verifyUser(ctx)) {
                    TransactionController.postTransaction(ctx);
                } else {
                    ctx.status(HttpStatus.UNAUTHORIZED_401);
                }
            });
        });

        //Dual-purpose get requests
        app.get("/api/v1/accounts", ctx -> {
            monitor.getRequestLatency().record(() -> {
                if (AuthenticationController.verifyUser(ctx)) {
                    AccountController.getAccounts(ctx);
                } else {
                    ctx.status(HttpStatus.UNAUTHORIZED_401);
                }
            });
        });

        app.get("/api/v1/transactions", ctx -> {
            monitor.getRequestLatency().record(() -> {
                if (AuthenticationController.verifyUser(ctx)) {
                    TransactionController.getAllTransactions(ctx);
                } else {
                    ctx.status(HttpStatus.UNAUTHORIZED_401);
                }
            });
        });

        app.get("/api/v1/accounts/{id}", ctx -> {
            monitor.getRequestLatency().record(() -> {
                if (AuthenticationController.verifyUser(ctx)) {
                    AccountController.getAccountsById(ctx);
                } else {
                    ctx.status(HttpStatus.UNAUTHORIZED_401);
                }
            });
        });

        app.patch("/api/v1/accounts", ctx -> {
            monitor.getRequestLatency().record(() -> {
                if (AuthenticationController.verifyUser(ctx)) {
                    AccountController.updateAccountStatus(ctx);
                } else {
                    ctx.status(HttpStatus.UNAUTHORIZED_401);
                }
            });
        });

        app.patch("/api/v1/transactions", ctx -> {
            monitor.getRequestLatency().record(() -> {
                if (AuthenticationController.verifyUser(ctx)) {
                    TransactionController.updateTranfer(ctx);
                } else {
                    ctx.status(HttpStatus.UNAUTHORIZED_401);
                }
            });
        });

        app.get("/metrics", ctx -> {
            ctx.result(monitor.getRegistry().scrape());
        });

        app.get("/coffee", ctx -> {
            ctx.result("I'm a teapot!");
            ctx.status(HttpStatus.IM_A_TEAPOT_418);
        });

        app.get("/500", ctx -> {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        });

        app.after(ctx -> {
            User u = ctx.sessionAttribute("user");
            if(ctx.status() == 500){
                monitor.incrementServerError();
            }
        });
    }

}
