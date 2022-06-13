package com.revature.service;

import com.revature.dao.UserDao;

public class AuthenticationService {
	
	private static final UserDao uDao = new UserDao();
	
	public static boolean authenticate(String username, String password) {
		User user = uDao.getUserByUsername(username);
		if (user == null || !user.getPassword().equals(password)) {
			return false;
		} else {
			return true;
		}
	}
	
}
