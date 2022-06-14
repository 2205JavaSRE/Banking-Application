package com.revature.models;

public enum TransactionStatus {
    PENDING,
    APPROVED,
    DENIED;

    public static TransactionStatus fromOrdinal(int n){
        switch(n){
            case 0:
                return PENDING;
            case 1:
                return APPROVED;
            case 2:
                return DENIED;
            default:
                return null;
        }
    }
}
