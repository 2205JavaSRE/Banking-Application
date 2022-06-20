package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.models.User;

public class AuthenticationService {
	
	private static final UserDao uDao = new UserDao();
	
	private AuthenticationService() {}
	
	public static boolean authenticate(String username, String password) {
		User user = uDao.getUserByUsername(username);
		System.out.println("Authentication Service.authenticate: " + user);
		if (user == null || !user.getPassword().equals(password)) {
			return false;
		} else {
			return true;
		}
	}
	
}
