package com.revature.service;

import java.sql.Timestamp;
import java.util.List;

import com.revature.dao.AccountDao;
import com.revature.dao.TransactionDao;
import com.revature.models.*;

public class TransactionService {
	private static final AccountDao aDao = new AccountDao();
	private static final TransactionDao tDao = new TransactionDao();
	
	private TransactionService() {}
	
	public static boolean postTransaction(Transaction t, User u) {
		boolean bool = false;
		Account a = aDao.getAccountByAccountID(t.getOriginAccount());
	
		if((a.getPrimaryOwnerID() == u.getUserID() || a.getJointOwnerID() == u.getUserID()) && a.isApproved()){
			switch(t.getTransactionType()){
				case DEPOSIT:
					bool = deposit(t, a);
					break;
				case WITHDRAWAL:
					bool = withdrawal(t, a);
					break;
				case TRANSFER:
					bool = initTransfer(t, a);
					break;
			}
		}
		return bool;
	}

	private static boolean deposit(Transaction t, Account a){
		if(t.getTransactionAmount() >= 0){
			t.setDestinationAccount(a.getAccountID());
			t.setTransactionStatus(TransactionStatus.APPROVED);
			a.setBalance(a.getBalance() + t.getTransactionAmount());
			aDao.updateAccount(a);
			t.setTimestamp(new Timestamp(System.currentTimeMillis()));
			tDao.insertTransaction(t);
			return true;
		}else{
			return false;
		}

	}

	private static boolean withdrawal(Transaction t, Account a){
		if(t.getTransactionAmount() >= 0 && t.getTransactionAmount() <= a.getBalance()){
			t.setDestinationAccount(a.getAccountID());
			t.setTransactionStatus(TransactionStatus.APPROVED);
			a.setBalance(a.getBalance() - t.getTransactionAmount());
			aDao.updateAccount(a);
			t.setTimestamp(new Timestamp(System.currentTimeMillis()));
			tDao.insertTransaction(t);
			return true;
		}else{
			return false;
		}
	}

	private static boolean initTransfer(Transaction t, Account a){
		Account destAccount = aDao.getAccountByAccountID(t.getDestinationAccount());
		if(destAccount != null
				&& destAccount.isApproved()
				&& t.getTransactionAmount() >= 0
				&& t.getTransactionAmount() <= a.getBalance()){
			t.setTransactionStatus(TransactionStatus.PENDING);
			a.setBalance(a.getBalance() - t.getTransactionAmount());
			aDao.updateAccount(a);
			t.setTimestamp(new Timestamp(System.currentTimeMillis()));
			tDao.insertTransaction(t);
			return true;
		}else{
			return false;
		}
	}

	public static boolean updateTransfer(Transaction t, User u){
		Transaction real = tDao.getTransactionByID(t.getTransactionID());
		Account a = aDao.getAccountByAccountID(real.getDestinationAccount());
		if(t.getTransactionStatus() != TransactionStatus.PENDING && (a.getPrimaryOwnerID() == u.getUserID() || a.getJointOwnerID() == u.getUserID())){
			real.setTransactionStatus(t.getTransactionStatus());
			if(real.getTransactionStatus() == TransactionStatus.APPROVED){
				a.setBalance(a.getBalance() + real.getTransactionAmount());
				aDao.updateAccount(a);
				tDao.updateTransactionStatus(real);
			}else{
				Account origin = aDao.getAccountByAccountID(real.getOriginAccount());
				origin.setBalance(origin.getBalance() + real.getTransactionAmount());
				aDao.updateAccount(origin);
				tDao.updateTransactionStatus(real);
			}
			return true;
		}else {
			return false;
		}
	}

	public static List<Transaction> getAllTransactions() {
		return tDao.getAllTransactions();
		
	}

}
