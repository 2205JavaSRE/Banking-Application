package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.models.User;

public class UserService {
	
	private static final UserDao uDao = new UserDao();
	
	public static boolean createUser(User u) {
		// TODO Creat false statement if unsuccessful
		uDao.insertUser(u);
		return true;
	}
}
