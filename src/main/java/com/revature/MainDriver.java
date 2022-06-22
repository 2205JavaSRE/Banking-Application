package com.revature;
import com.revature.controller.RequestMappings;
import com.revature.dao.*;
import com.revature.models.*;
import com.revature.util.Monitor;
import io.javalin.Javalin;
import io.javalin.plugin.metrics.MicrometerPlugin;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MainDriver {
    public static Monitor monitor = new Monitor();
    public static void main(String[] args){
        Javalin myApp = Javalin.create(
                config -> {
                    config.registerPlugin(new MicrometerPlugin(monitor.getRegistry()));
                }
        ).start(7700);
        RequestMappings.configureRoutes(myApp, monitor);
    }
}
