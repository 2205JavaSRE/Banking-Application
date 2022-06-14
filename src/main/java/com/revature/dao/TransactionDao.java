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
        String sql = "insert into transactions (transaction_id, transaction_type, origin_account, destination_account, transaction_amount, transaction_time, status) " +
                "values (?,?,?,?,?,?,?)";

        Connection connection = ConnectionFactory.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setInt(1, transaction.getTransactionID());
            ps.setString(2, transaction.getTransactionType().name());
            ps.setInt(3, transaction.getOriginAccount());
            ps.setInt(4, transaction.getDestinationAccount());
            ps.setDouble(5, transaction.getTransactionAmount());
            ps.setTimestamp(6, transaction.getTimestamp());
            ps.setString(7, transaction.getTransactionStatus().name());
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
                        rs.getInt("origin_account"),
                        rs.getInt("destination_account"),
                        rs.getDouble("transaction_amount"),
                        rs.getTimestamp("timestamp"),
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
                            rs.getInt("origin_account"),
                            rs.getInt("destination_account"),
                            rs.getDouble("transaction_amount"),
                            rs.getTimestamp("timestamp"),
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
        String sql = "update transactions set transaction_status = ? where transaction_id = ?";

        Connection connection = ConnectionFactory.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, TransactionStatus.APPROVED.name());
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
                        rs.getInt("origin_account"),
                        rs.getInt("destination_account"),
                        rs.getDouble("transaction_amount"),
                        rs.getTimestamp("timestamp"),
                        TransactionStatus.valueOf(rs.getString("status"))
                );
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return transaction;
    }
}
