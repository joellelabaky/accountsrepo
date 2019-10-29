package moneyTransfer.accounts.entity;

import java.math.BigDecimal;

public class Transfer {

	private int accountFromId;
	private int accountToId;
	private BigDecimal amount;

	public int getAccountFromId() {
		return accountFromId;
	}

	public void setAccountFromId(int accountFromId) {
		this.accountFromId = accountFromId;
	}

	public int getAccountToId() {
		return accountToId;
	}

	public void setAccountToId(int accountToId) {
		this.accountToId = accountToId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}