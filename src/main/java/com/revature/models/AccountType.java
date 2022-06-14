package com.revature.models;

public enum AccountType {
    CHECKING,
    SAVINGS;

    public static AccountType fromOrdinal(int n){
        switch(n){
            case 0:
                return CHECKING;
            case 1:
                return SAVINGS;
            default:
                return null;
        }
    }
}
