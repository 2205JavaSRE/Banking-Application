package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.models.Account;
import com.revature.models.User;

public class AccountService {
	
	private static final AccountDao aDao = new AccountDao();
	
	public static boolean createAccount(Account a, User u) {
		//TODO Need user from controller session
		//TODO false stuff
		aDao.insertAccount(a, );
	}
}
