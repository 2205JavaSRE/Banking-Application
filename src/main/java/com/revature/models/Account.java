package com.revature.models;

public class Account {
    private int accountID;
    private int primaryOwnerID;
    private int secondaryOwnerID;
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
    public Account(int primaryOwnerID, AccountType accountType, double balance) {
        this.accountID = -1;
        this.primaryOwnerID = primaryOwnerID;
        this.secondaryOwnerID = -1;
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
        this.secondaryOwnerID = secondaryOwnerID;
        this.accountType = accountType;
        this.balance = balance;
        this.approved = false;
    }

    /**
     * To be used for EXISTING ACCOUNTS ONLY.
     * @param accountID
     * @param primaryOwnerID
     * @param secondaryOwnerID
     * @param accountType
     * @param balance
     * @param approved
     */
    public Account(int accountID, int primaryOwnerID, int secondaryOwnerID, AccountType accountType, double balance, boolean approved) {
        this.accountID = accountID;
        this.primaryOwnerID = primaryOwnerID;
        this.secondaryOwnerID = secondaryOwnerID;
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

    public int getSecondaryOwnerID() {
        return secondaryOwnerID;
    }

    public void setSecondaryOwnerID(int secondaryOwnerID) {
        this.secondaryOwnerID = secondaryOwnerID;
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
                ", secondaryOwnerID=" + secondaryOwnerID +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", approved=" + approved +
                '}';
    }
}
