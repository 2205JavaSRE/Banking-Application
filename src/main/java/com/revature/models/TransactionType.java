package com.revature.models;

public enum TransactionType {
    WITHDRAWAL,
    DEPOSIT,
    TRANSFER;

    public static TransactionType fromOrdinal(int n){
        switch(n){
            case 0:
                return WITHDRAWAL;
            case 1:
                return DEPOSIT;
            case 2:
                return TRANSFER;
            default:
                return null;
        }
    }
}
