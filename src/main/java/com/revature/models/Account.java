package com.revature.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    private int accountID;
    private int primaryOwnerID;
    private int jointOwnerID;
    private AccountType accountType;
    private double balance;
    private boolean approved;

    public Account(){
        super();
    }
    /**
     * To be used for NEW ACCOUNTS ONLY or for authentication purposes.
     * accountID is set to -1,
     * secondaryOwnerID is set to -1,
     * approve is set to false.
     * @param primaryOwnerID
     * @param accountType
     * @param balance
     */
    @JsonCreator
    public Account(@JsonProperty("primaryOwnerID") int primaryOwnerID,
                   @JsonProperty("accountType") AccountType accountType,
                   @JsonProperty("jointOwnerID") int jointOwnerID,
                   @JsonProperty("balance") double balance) {
        this.accountID = -1;
        this.primaryOwnerID = primaryOwnerID;
        this.jointOwnerID = jointOwnerID;
        this.accountType = accountType;
        this.balance = balance;
        this.approved = false;
    }

    /**
     * To be used for NEW JOINT ACCOUNTS ONLY or for Authentication purposes.
     * accountID is set to -1
     * approved is set to false.
     * @param primaryOwnerID
     * @param secondaryOwnerID
     * @param accountType
     * @param balance
     */
    public Account(int primaryOwnerID, int secondaryOwnerID, AccountType accountType, double balance) {
        this.accountID = -1;
        this.primaryOwnerID = primaryOwnerID;
        this.jointOwnerID = secondaryOwnerID;
        this.accountType = accountType;
        this.balance = balance;
        this.approved = false;
    }

    /**
     * To be used for EXISTING ACCOUNTS ONLY.
     * @param accountID
     * @param primaryOwnerID
     * @param jointOwnerID
     * @param accountType
     * @param balance
     * @param approved
     */
    public Account(int accountID, int primaryOwnerID, int jointOwnerID, AccountType accountType, double balance, boolean approved) {
        this.accountID = accountID;
        this.primaryOwnerID = primaryOwnerID;
        this.jointOwnerID = jointOwnerID;
        this.accountType = accountType;
        this.balance = balance;
        this.approved = approved;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getPrimaryOwnerID() {
        return primaryOwnerID;
    }

    public void setPrimaryOwnerID(int primaryOwnerID) {
        this.primaryOwnerID = primaryOwnerID;
    }

    public int getJointOwnerID() {
        return jointOwnerID;
    }

    public void setJointOwnerID(int jointOwnerID) {
        this.jointOwnerID = jointOwnerID;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", primaryOwnerID=" + primaryOwnerID +
                ", secondaryOwnerID=" + jointOwnerID +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", approved=" + approved +
                '}';
    }
}
