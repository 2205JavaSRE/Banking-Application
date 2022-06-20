package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.models.User;

public class UserService {
	
	private static final UserDao uDao = new UserDao();
	
	private UserService() {}
	
	public static boolean createUser(User u) {
		System.out.println("AuthenticationController.createUser: " + u);
		System.out.println("exists ?:" + uDao.existsByName(u.getUsername()));
		if (uDao.existsByName(u.getUsername())) {
			return false;
		} else {
			uDao.insertUser(u);
			return true;
		}
	}

	public static User getUser(String username) {
		return uDao.getUserByUsername(username);
	}
}
