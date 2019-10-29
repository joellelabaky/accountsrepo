package moneyTransfer.accounts.repository;

import java.util.concurrent.ConcurrentHashMap;

import moneyTransfer.accounts.entity.Account;

public class AccountRepository {
	
	private static ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<>();

	public static ConcurrentHashMap<Integer, Account> getAccounts() {
		return accounts;
	}

	public static void setAccounts(ConcurrentHashMap<Integer, Account> accounts) {
		AccountRepository.accounts = accounts;
	}

	public void addAccount(int id, Account account) {
		accounts.put(id, account);	
	}

	public Account getAccount(int accountId) {
		return accounts.get(accountId);
	}  
}