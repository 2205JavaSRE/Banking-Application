package com.revature.dao;
import com.revature.models.Account;

public interface AccountDaoInterface {
    public void insertAccount(Account account, User user);
    public void updateAcount(Account account);
    public void Account getAccountByAccountID(int accountID);
    public ArrayList<Account> getAccountsByOwnerID(int ownerID);
    public ArrayList<Account> getAccountsByApproval(approval_type approval);

}
