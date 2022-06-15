package com.revature.service;
import java.util.List;

import com.revature.dao.AccountDao;
import com.revature.dao.UserDao;
import com.revature.models.Account;
import com.revature.models.AccountType;
import com.revature.models.User;

public class AccountService {
	
	private static final AccountDao aDao = new AccountDao();
	private static final UserDao uDao = new UserDao();
	
	private AccountService() {}
	
	public static boolean createAccount(Account a, User u) {
		if (a.getBalance() < 0) {
			return false;
		} else {
			if (a.getJointOwnerID() != -1 && uDao.existsByUserID(a.getJointOwnerID())) {
				aDao.insertAccount(a, u);
			} else {
				a.setJointOwnerID(a.getPrimaryOwnerID());
				aDao.insertAccount(a, u);
			}
			return true;
		}
	}

	public static List<Account> getAllPendingAccounts(){
		return aDao.getAccountsByApproval(false);
	}

	public static List<Account> getAccountsByUserId(int userID) {
		return aDao.getAccountsByOwnerID(userID);
	}
	
	public static void updateAccountStatus(Account a) {
		Account target = aDao.getAccountByAccountID(a.getAccountID());
		target.setApproved(a.isApproved());
		aDao.updateAccount(target);
	}



}
