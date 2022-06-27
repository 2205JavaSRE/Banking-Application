package com.revature.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.revature.dao.AccountDao;
import com.revature.dao.UserDao;
import com.revature.models.Account;
import com.revature.models.AccountType;
import com.revature.models.User;

public class AccountServiceTest {
	
	private User braylen;
	private User mike;
	
	private Account goodOldAccount;
	private Account goodNewAccount;
	private Account badJointNewAccount;
	private Account badBalanceNewAccount;
	private Account updatedStatusAccount;
	
	ArrayList<Account> pendingAccounts = new ArrayList<>();
	ArrayList<Account> accountsByUserId = new ArrayList<>();
	
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	@Mock
	private AccountDao aDao;
	@Mock
	private UserDao uDao;

	@BeforeClass
	public static void setUpBeforeClass() {
		System.out.println("Begin AccountService Testing.");
	}

	@AfterClass
	public static void tearDownAfterClass() {
		System.out.println("End AccountService Testing");
	}

	@Before
	public void setUp() {
		System.out.println("---------------------------");
				
		braylen = new User(1, "Braylen", "Strain", "braylenstrain", "tnnfebs", true);
		mike = new User(4, "Mike", "Wazowski", "oneeyedguy", "imgreen", false);
		
		goodOldAccount = new Account(2, 4, 4, AccountType.SAVINGS, 12.36, true);
		goodNewAccount = new Account(7, 4, 5, AccountType.CHECKING, 500, false);
		badJointNewAccount = new Account(7, 4, 7, AccountType.CHECKING, 500, false);
		badBalanceNewAccount = new Account(7, 4, 5, AccountType.CHECKING, -.01, false);
		updatedStatusAccount = new Account(7, 0, 0, null, 0.0, true);
		
		pendingAccounts.add(goodNewAccount);
		
		accountsByUserId.add(goodOldAccount); 
		accountsByUserId.add(goodNewAccount);
		
		AccountService.uDao=uDao;
		AccountService.aDao=aDao;
	}

	@After
	public void tearDown() {
		braylen = null;
		mike = null;
		goodOldAccount = null;
		goodNewAccount = null;
		badJointNewAccount = null;
		badBalanceNewAccount = null;
		pendingAccounts.clear();
		accountsByUserId.clear();
		
		System.out.println("---------------------------");
	}

	@Test
	public void testCreateAccount() {
		System.out.println("testCreateAccount() started.");
		
		when(uDao.existsByUserID(goodNewAccount.getJointOwnerID())).thenReturn(true);
		assertTrue(AccountService.createAccount(goodNewAccount, mike));
		verify(aDao).insertAccount(goodNewAccount, mike);
		
		when(uDao.existsByUserID(badJointNewAccount.getJointOwnerID())).thenReturn(false);
		assertTrue(AccountService.createAccount(badJointNewAccount, mike));
		verify(aDao).insertAccount(badJointNewAccount, mike);
		
		assertFalse(AccountService.createAccount(badBalanceNewAccount, mike));
		
		System.out.println("testCreateAccount() completed.");
	}

	@Test
	public void testGetAllPendingAccounts() {
		System.out.println("testGetAllPendingAccounts() started.");
		
		when(aDao.getAccountsByApproval(false)).thenReturn(pendingAccounts);
		assertEquals(pendingAccounts, AccountService.getAllPendingAccounts());
		
		System.out.println("testGetAllPendingAccounts() completed.");
	}

	@Test
	public void testGetAccountsByUserId() {
		System.out.println("testGetAccountsByUserId() started.");
		
		when(aDao.getAccountsByOwnerID(4)).thenReturn(accountsByUserId);
		when(aDao.getAccountsByOwnerID(1)).thenReturn(new ArrayList<Account>());
		assertEquals(accountsByUserId, AccountService.getAccountsByUserId(mike.getUserID()));
		assertEquals(new ArrayList<Account>(), AccountService.getAccountsByUserId(braylen.getUserID()));
		
		System.out.println("testGetAccountsByUserId() completed.");
	}

	@Test
	public void testUpdateAccountStatus() {
		System.out.println("testUpdateAccountStatus() started.");
		
		when(aDao.getAccountByAccountID(7)).thenReturn(goodNewAccount);
		AccountService.updateAccountStatus(updatedStatusAccount);
		assertTrue(goodNewAccount.isApproved());
		verify(aDao).updateAccount(goodNewAccount);
		
		System.out.println("testUpdateAccountStatus() completed.");
	}

}
