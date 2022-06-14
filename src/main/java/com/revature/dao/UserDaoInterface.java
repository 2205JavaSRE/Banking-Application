package com.revature.dao;

import com.revature.models.User;

import java.util.ArrayList;

public interface UserDaoInterface {
    public void insertUser(User user);
    public User getUserByUsername(String username);
    public ArrayList<User> getAllUsers();
    public boolean existsByName(String username);
}
