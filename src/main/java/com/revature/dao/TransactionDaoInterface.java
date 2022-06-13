package com.revature.dao;
import com.revature.models.Transaction;
public interface TransactionDaoInterface {
    public void insertTransaction(Transaction transaction);
    public ArrayList<Transaction> getTransactions();
    public ArrayList<Transaction> getPendingTransactionsByDestinationID(int destinationID);
    public void completeTransaction(Transaction transaction);
    public Transaction getTransactionByID(int transactionID);

}
