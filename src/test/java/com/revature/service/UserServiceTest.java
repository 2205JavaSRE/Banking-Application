package com.revature.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.revature.dao.UserDao;
import com.revature.models.User;

public class UserServiceTest {
	
	User oldUser;
	User newUser;
	
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	@Mock
	private UserDao uDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Begin UserService Testing.");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("End UserService Testing");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("---------------------------");

		oldUser = new User(1, "Braylen", "Strain", "braylenstrain", "tnnfebs", true);
		newUser = new User(2, "Mark", "Cuban", "markcuban", "richguy", false);
		
		UserService.uDao = uDao;
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("---------------------------");
	}

	@Test
	public void testCreateUser() {
		System.out.println("testCreateUser() started.");
		
		when(uDao.existsByName("braylenstrain")).thenReturn(true);
		assertFalse(UserService.createUser(oldUser));
		verify(uDao, times(0)).insertUser(oldUser);
		
		when(uDao.existsByName("markcuban")).thenReturn(false);
		assertTrue(UserService.createUser(newUser));
		verify(uDao).insertUser(newUser);
		
		System.out.println("testCreateUser() completed.");
	}

	@Test
	public void testGetUser() {
		System.out.println("testGetUser() started.");
		
		when(uDao.getUserByUsername("braylenstrain")).thenReturn(oldUser);
		assertEquals(oldUser, UserService.getUser("braylenstrain"));
		
		when(uDao.getUserByUsername("alfred")).thenReturn(null);
		assertEquals(null, UserService.getUser("alfred"));
		
		System.out.println("testGetUser() completed.");
	}
	
	@Test
	public void testExistsByUserID() {
		System.out.println("testExistsByUserID() started.");
		
		when(uDao.existsByUserID(1)).thenReturn(true);
		assertEquals(true, UserService.existsByUserID(1));
		
		when(uDao.existsByUserID(87)).thenReturn(false);
		assertEquals(false, UserService.existsByUserID(87));
		
		System.out.println("testExistsByUserID() completed.");
	}

}
