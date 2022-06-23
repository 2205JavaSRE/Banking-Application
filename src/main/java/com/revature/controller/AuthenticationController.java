package com.revature.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.revature.models.User;
import com.revature.service.AuthenticationService;
import com.revature.service.UserService;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import javalinjwt.JWTGenerator;
import javalinjwt.JWTProvider;
import javalinjwt.JavalinJWT;

import java.util.Optional;

import org.eclipse.jetty.http.HttpStatus;

public class AuthenticationController {
	//Choose any algorithm and secret key for JWT
	private static Algorithm algorithm = Algorithm.HMAC256("TNNFEtnnfe"); //TODO Make ENV variable
	//For JWT
	private static JWTGenerator<User> generator = (user, alg) -> {
        JWTCreator.Builder token = JWT.create()
                .withClaim("userID", user.getUserID())
                .withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .withClaim("username", user.getUsername())
                .withClaim("password", user.getPassword())
                .withClaim("isEmployee", user.isEmployee());
        return token.sign(alg);
    };
    //For JWT
    private static JWTVerifier verifier = JWT.require(algorithm).build();
    //For JWT
    private static JWTProvider provider = new JWTProvider(algorithm, generator, verifier);
    //decodeHandler using cookies for JWT
    static Handler decodeHandler = JavalinJWT.createCookieDecodeHandler(provider);
    
    //DecodeHandler using Header for JWT
    //static Handler decodeHandler = JavalinJWT.createHeaderDecodeHandler(provider);
	
	private AuthenticationController() {}
	
    public static void authenticate(Context ctx){
        User login = ctx.bodyAsClass(User.class);
        boolean access = AuthenticationService.authenticate(login.getUsername(), login.getPassword());
        if(access){
            ctx.status(HttpStatus.OK_200);
            User u = UserService.getUser(login.getUsername());
            String token = provider.generateToken(u);
            
            //Store JWT in cookie (Can use an optional third argument to set an expiration of the cookie in seconds (int))
            ctx.cookie("jwt", token);
            
            //Return JWT as JSON (must manually put in to Authorization in Postman)
            //ctx.json(new JWTResponse(token));
        }else {
            ctx.status(HttpStatus.UNAUTHORIZED_401);
        }
    }


    public static boolean verifyUser(Context ctx){
    	//Get JWT from Header
    	//Optional<DecodedJWT> decodedJWT = JavalinJWT.getTokenFromHeader(ctx).flatMap(provider::validateToken);
    	
    	//Get JWT from Cookie
    	Optional<DecodedJWT> decodedJWT = JavalinJWT.getTokenFromCookie(ctx).flatMap(provider::validateToken);
    	
    	return decodedJWT.isPresent();
    }
    
    public static void createUser(Context ctx) {
    	try {
    		User u = ctx.bodyAsClass(User.class);
        	if (!UserService.createUser(u)) {
        		ctx.status(HttpStatus.BAD_REQUEST_400);
        	}else{
                ctx.status(HttpStatus.CREATED_201);
            }
    	} catch (Exception e) {
    		ctx.status(HttpStatus.BAD_REQUEST_400);
    	}
    }
    
}
