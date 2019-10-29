package moneyTransfer.accounts.service;

import java.math.BigDecimal;

import moneyTransfer.accounts.entity.Account;

public class TransferServiceImpl implements TransferService{
	
	public boolean transfer(Account from ,Account to, BigDecimal amount) {
		return from.transfer(to, amount);
	}

}
