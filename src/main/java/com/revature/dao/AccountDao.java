package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.models.TransactionStatus;

import java.util.ArrayList;

public class AccountDao implements AccountDaoInterface {

    @Override
    public void insertAccount(Account account, User user) {

    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public Account getAccountByAccountID(int accountID) {
        return null;
    }

    @Override
    public ArrayList<Account> getAccountsByOwnerID(int ownerID) {
        return null;
    }

    @Override
    public ArrayList<Account> getAccountsByApproval(boolean approvalStatus) {
        return null;
    }
}
