package com.revature.service;
import java.util.*;
import com.revature.dao.AccountDao;
import com.revature.models.Account;
import com.revature.models.AccountType;
import com.revature.models.User;

public class AccountService {
	
	private static final AccountDao aDao = new AccountDao();
	
	public static boolean createAccount(Account a, User u) {
		if (a.getBalance() < 0 || !(a.getAccountType() instanceof AccountType)) {
			return false;
		} else {
			aDao.insertAccount(a, u);
			return true;
		}
	}

	public static List<Account> getAllPendingAccounts(){
		return aDao.getAccountsByApproval(false);
	}

	public static List<Account> getAccountsById(int userID) {
		return aDao.getAccountsByOwnerID(userID);
	}
	
	//TODO May change to enum or add delete in dao
	public static void updateAccountStatus(Account a) {
		Account target = aDao.getAccountByAccountID(a.getAccountID());
		target.setApproved(a.isApproved());
		aDao.updateAccount(target);
	}



}
