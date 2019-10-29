package moneyTransfer.accounts.entity;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import moneyTransfer.accounts.repository.TransactionRepository;

public class Transaction {

	private int id;
	private int accountId;
	private BigDecimal amount;
	private static int globalld = 0;
	private Lock lock = new ReentrantLock();
	private final static TransactionRepository transactionRepository = new TransactionRepository();

	public Transaction(BigDecimal amount, int accountId) {
		lock.lock();
		this.id = ++globalld;
		lock.unlock();
		this.amount = amount;
		this.accountId = accountId;

		transactionRepository.addTransaction(this.id, this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
