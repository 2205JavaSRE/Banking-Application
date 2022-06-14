package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;

import java.util.ArrayList;

public interface AccountDaoInterface {
    public void insertAccount(Account account, User user);
    public void updateAccount(Account account);
    public Account getAccountByAccountID(int accountID);
    public ArrayList<Account> getAccountsByOwnerID(int ownerID);
    public ArrayList<Account> getAccountsByApproval(boolean approvalStatus);

}
