package com.revature.dao;

import com.revature.models.Transaction;
import java.util.ArrayList;

public class TransactionDao implements TransactionDaoInterface {
    @Override
    public void insertTransaction(Transaction transaction) {

    }

    @Override
    public ArrayList<Transaction> getTransactions() {
        return null;
    }

    @Override
    public ArrayList<Transaction> getPendingTransactionsByDestinationID(int destinationID) {
        return null;
    }

    @Override
    public void completeTransaction(Transaction transaction) {

    }
    @Override
    public Transaction getTransactionByID(int transactionID) {
        return null;
    }
}
