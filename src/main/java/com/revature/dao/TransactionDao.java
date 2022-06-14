package com.revature.dao;

import com.revature.models.Transaction;
import com.revature.models.TransactionStatus;
import com.revature.models.TransactionType;
import com.revature.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;

public class TransactionDao implements TransactionDaoInterface {
    @Override
    public void insertTransaction(Transaction transaction) {
        //int transactionID, TransactionType transactionType, int originAccount, int destinationAccount,
        // double transactionAmount, Timestamp timestamp, TransactionStatus transactionStatus
        String sql = "insert into transactions (transaction_type, origin_account_id, destination_account_id, transaction_amount, transaction_time, status) " +
                "values (?::t_type,?,?,?,?,?::t_status)";

        Connection connection = ConnectionFactory.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, transaction.getTransactionType().name());
            ps.setInt(2, transaction.getOriginAccount());
            ps.setInt(3, transaction.getDestinationAccount());
            ps.setDouble(4, transaction.getTransactionAmount());
            ps.setTimestamp(5, transaction.getTimestamp());
            ps.setString(6, transaction.getTransactionStatus().name());
            ps.execute();


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Transaction> getAllTransactions() {
        //int transactionID, TransactionType transactionType, int originAccount, int destinationAccount,
        // double transactionAmount, Timestamp timestamp, TransactionStatus transactionStatus
        String sql = "select * from transactions";
        Connection connection = ConnectionFactory.getConnection();
        ArrayList<Transaction> transactions = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        TransactionType.valueOf(rs.getString("transaction_type")),
                        rs.getInt("origin_account_id"),
                        rs.getInt("destination_account_id"),
                        rs.getDouble("transaction_amount"),
                        rs.getTimestamp("transaction_time"),
                        TransactionStatus.valueOf(rs.getString("status"))
                );
                transactions.add(transaction);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public ArrayList<Transaction> getPendingTransactionsByDestinationID(int destinationID) {
        String sql = "select * from transactions where destination_id = ?";

        Connection connection = ConnectionFactory.getConnection();
        ArrayList<Transaction> transactions = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, destinationID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                if( TransactionStatus.valueOf(rs.getString("status")) == TransactionStatus.PENDING ){

                    Transaction transaction = new Transaction(
                            rs.getInt("transaction_id"),
                            TransactionType.valueOf(rs.getString("transaction_type")),
                            rs.getInt("origin_account_id"),
                            rs.getInt("destination_account_id"),
                            rs.getDouble("transaction_amount"),
                            rs.getTimestamp("transaction_time"),
                            TransactionStatus.valueOf(rs.getString("status"))
                    );
                    transactions.add(transaction);
                }


            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public void completeTransaction(Transaction transaction) {
        String sql = "update transactions set status = ?::t_status where transaction_id = ?";

        Connection connection = ConnectionFactory.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, TransactionStatus.APPROVED.name());
            ps.setInt(2, transaction.getTransactionID());
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void updateTransactionStatus(Transaction transaction){
        String sql = "update transactions set status = ?::t_status where transaction_id = ?";

        Connection connection = ConnectionFactory.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, transaction.getTransactionStatus().name());
            ps.setInt(2, transaction.getTransactionID());
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Transaction getTransactionByID(int transactionID) {
        String sql = "select * from transactions where transaction_id = ?";
        Connection connection = ConnectionFactory.getConnection();
        Transaction transaction = null;

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,transactionID);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        TransactionType.valueOf(rs.getString("transaction_type")),
                        rs.getInt("origin_account_id"),
                        rs.getInt("destination_account_id"),
                        rs.getDouble("transaction_amount"),
                        rs.getTimestamp("transaction_time"),
                        TransactionStatus.valueOf(rs.getString("status"))
                );
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return transaction;
    }
}
