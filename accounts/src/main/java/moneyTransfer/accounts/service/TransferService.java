package moneyTransfer.accounts.service;

import java.math.BigDecimal;

import moneyTransfer.accounts.entity.Account;

public interface TransferService {

	public boolean transfer(Account from ,Account to, BigDecimal amount) ;

}
