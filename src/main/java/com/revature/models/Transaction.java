package com.revature.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class Transaction {
    private int transactionID;
    private TransactionType transactionType;
    private int originAccount;
    private int destinationAccount;
    private double transactionAmount;
    private Timestamp timestamp;
    private TransactionStatus transactionStatus;

    public Transaction(){
        super();
    }
    /**
     * To be used for NEW TRANSACTIONS ONLY or for Authentication purposes.
     * transactionID is set to -1,
     * transactionStatus is set to PENDING.
     * @param transactionType
     * @param originAccount
     * @param destinationAccount
     * @param transactionAmount
     * @param timestamp
     * @param transactionStatus
     */
    @JsonCreator
    public Transaction(@JsonProperty("transactionType") TransactionType transactionType,
                       @JsonProperty("originAccount") int originAccount,
                       @JsonProperty("destinationAccount") int destinationAccount,
                       @JsonProperty("transactionAmount") double transactionAmount,
                       @JsonProperty("timestamp") Timestamp timestamp,
                       @JsonProperty("transactionStatus")TransactionStatus transactionStatus) {
        this.transactionID = -1;
        this.transactionType = transactionType;
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
        this.transactionAmount = transactionAmount;
        this.timestamp = timestamp;
        this.transactionStatus = TransactionStatus.PENDING;
    }

    /**
     * To be used for EXISTING ACCOUNTS ONLY.
     * @param transactionID
     * @param transactionType
     * @param originAccount
     * @param destinationAccount
     * @param transactionAmount
     * @param timestamp
     * @param transactionStatus
     */
    public Transaction(int transactionID,
                       TransactionType transactionType,
                       int originAccount,
                       int destinationAccount,
                       double transactionAmount,
                       Timestamp timestamp,
                       TransactionStatus transactionStatus) {
        this.transactionID = transactionID;
        this.transactionType = transactionType;
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
        this.transactionAmount = transactionAmount;
        this.timestamp = timestamp;
        this.transactionStatus = transactionStatus;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public int getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(int originAccount) {
        this.originAccount = originAccount;
    }

    public int getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(int destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", transactionType=" + transactionType +
                ", originAccount=" + originAccount +
                ", destinationAccount=" + destinationAccount +
                ", transactionAmount=" + transactionAmount +
                ", timestamp=" + timestamp +
                ", transactionStatus=" + transactionStatus +
                '}';
    }
}
