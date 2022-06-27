package com.revature.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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

public class AuthenticationServiceTest {
	
	private User nullUser;
	private User goodUser;
	
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	@Mock
	private UserDao uDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Begin AuthenticationService Testing.");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("End AuthenticationService Testing");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("---------------------------");
		
		AuthenticationService.uDao = uDao;
		
		nullUser = null;
		goodUser = new User(1, "Braylen", "Strain", "braylenstrain", "tnnfebs", true);
		
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("---------------------------");
	}

	@Test
	public void testAuthenticate() {
		System.out.println("testAuthenticate() started.");
		
		when(uDao.getUserByUsername("DoesntExist")).thenReturn(nullUser);
		assertFalse(AuthenticationService.authenticate("DoesntExist", "password"));
		
		when(uDao.getUserByUsername("braylenstrain")).thenReturn(goodUser);
		assertTrue(AuthenticationService.authenticate("braylenstrain", "tnnfebs"));
		assertFalse(AuthenticationService.authenticate("braylenstrain", "password"));
		
		System.out.println("testAuthenticate() completed.");
	}

}
