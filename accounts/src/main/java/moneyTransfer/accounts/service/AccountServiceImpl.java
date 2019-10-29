package moneyTransfer.accounts.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import moneyTransfer.accounts.entity.Account;
import moneyTransfer.accounts.entity.Transaction;

public class AccountServiceImpl implements AccountService {

	public Map getAccounts() {

		return Account.getAccounts();
	}

	public Account getAccount(int accountId) {
		return Account.getAccount(accountId);
	}

	public Account createAccount(BigDecimal balance) {
		return new Account(balance);
	}

	public Set<Transaction> getAccountTransactions(int accountId) {
		return Account.getAccount(accountId).getTransactions();
	}

	public BigDecimal getAccountBalance(int accountId) throws Exception {

		try {
			Account account = Account.getAccount(accountId);
			if (account != null) {
				return account.getBalance();
			} else
				throw new Exception("Account not found");
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
