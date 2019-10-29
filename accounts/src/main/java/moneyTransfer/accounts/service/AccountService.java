package moneyTransfer.accounts.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import moneyTransfer.accounts.entity.Account;
import moneyTransfer.accounts.entity.Transaction;

public interface AccountService {

	public Map getAccounts();

	public Account getAccount(int accountId);

	public Account createAccount(BigDecimal balance);

	public Set<Transaction> getAccountTransactions(int accountId);

	public BigDecimal getAccountBalance(int parseInt) throws Exception;

}
