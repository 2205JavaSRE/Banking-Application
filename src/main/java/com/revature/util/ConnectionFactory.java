package com.revature.util;

import com.revature.MainDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static String URL = System.getenv("db_url");
    private static String USERNAME = System.getenv("db_username");
    private static String PASSWORD = System.getenv("db_password");

    private static Connection connection;

    public static Connection getConnection() {

        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            MainDriver.monitor.incrementSqlCounter();
        }

        return connection;
    }

}
