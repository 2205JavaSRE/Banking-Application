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
    public static void main(String[] args){
        Monitor monitor = new Monitor();

        Javalin myApp = Javalin.create(
                config -> {
                    config.registerPlugin(new MicrometerPlugin(monitor.getRegistry()));
                }
        ).start(7400);
        RequestMappings.configureRoutes(myApp, monitor);
    }
    
    //TODO Delete later
    UserDao uDao = new UserDao();
    AccountDao aDao = new AccountDao();
    TransactionDao tDao = new TransactionDao();
//    (first_name, last_name, username, password)";
/*        User user = new User("joe", "pesci","vinny","yutes");
    uDao.insertUser(user);*/
//            String sql = "insert into accounts (primary_owner_id, account_type, balance, approved) values " +
//                    "((select user_id from users where username = ?),?,?,?)";
//    int primaryOwnerID, AccountType accountType, double balance
/*        Account account = new Account(-1,AccountType.CHECKING,999.99);
    User user2 = uDao.getUserByUsername("vinny");
    System.out.println(user2);
    aDao.insertAccount(account, user2);*/
//    (TransactionType transactionType,
//                           int originAccount,
//                           int destinationAccount,
//                           double transactionAmount,
//                           Timestamp timestamp,
//                           TransactionStatus transactionStatus)
/*        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Transaction transaction = new Transaction(TransactionType.TRANSFER, 4, 5, 0.77, timestamp, TransactionStatus.PENDING);
    System.out.println(transaction);
    tDao.insertTransaction(transaction);*/
/*        System.out.println(tDao.getTransactionByID(2));
    Transaction transaction2 = tDao.getTransactionByID(2);
    transaction2.setTransactionStatus(TransactionStatus.APPROVED);
    tDao.updateTransactionStatus(transaction2);*/
/*        ArrayList<User> users = uDao.getAllUsers();
    System.out.println(users);*/
/*        System.out.println(aDao.getAccountsByApproval(false));*/
    //test
}
