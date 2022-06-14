package com.revature.dao;

import com.revature.models.User;

import java.util.ArrayList;
public class UserDao implements UserDaoInterface{
    @Override
    public void insertEmployee(User user) {

    }
    @Override
    public User getEmployeeByUsername(String username) {
        return null;
    }

    @Override
    public ArrayList<User> getAllEmployees() {
        return null;
    }

    @Override
    public boolean existsByName(String username) {
        return false;
    }
}
