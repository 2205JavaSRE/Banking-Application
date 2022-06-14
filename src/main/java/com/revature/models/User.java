package com.revature.models;

public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isEmployee;

    public User(){
        super();
    }

    /**
     * To be used for NEW USERS ONLY or for Authentication purposes.
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     */
    public User(String firstName, String lastName, String username, String password) {
        this.userID = -1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isEmployee = false;
    }

    /**
     * To be used for EXISTING USERS ONLY.
     * @param userID
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     */
    public User(int userID, String firstName, String lastName, String username, String password, boolean isEmployee) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isEmployee = isEmployee;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
