package com.revature.service;
import java.util.*;
import com.revature.dao.AccountDao;
import com.revature.models.Account;
import com.revature.models.User;

public class AccountService {
	
	private static final AccountDao aDao = new AccountDao();
	
	public static boolean createAccount(Account a, User u) {
		//TODO false stuff
		aDao.insertAccount(a, u);
	}

	public static List<Account> getAllPendingAccounts(){
		return aDao.getAccountsByApproval(false);
	}



}
