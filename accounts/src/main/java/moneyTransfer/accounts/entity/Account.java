package moneyTransfer.accounts.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import moneyTransfer.accounts.repository.AccountRepository;

public class Account {

	private int id;
	private BigDecimal balance;
	private Set<Transaction> transactions = new HashSet<>();
	
    private static int globalld = 0;
    private static Lock lock = new ReentrantLock();
  
	private final static AccountRepository accountRepository = new AccountRepository();
	
    public Account(BigDecimal balance) {
		lock.lock();
		this.id = ++globalld;
		lock.unlock();
		this.balance = balance;
		accountRepository.addAccount(this.id, this);
	}
	
	public boolean transfer(Account to, BigDecimal amount) {
		// to prevent deadlock - in case 2 accounts are transferring to each other in same time
		Account lockl = (id < to.id) ? this : to;
		Account lock2 = (id < to.id) ? to : this;

		synchronized (lockl) {
			synchronized (lock2) {
				if (amount.compareTo(balance) > 0) {
					return false;
				}
				to.executeTransaction(amount);
				this.executeTransaction(amount.negate());
				return true;
			}
		}
	}

	private void executeTransaction(BigDecimal amount) {
		Transaction transaction = new Transaction(amount,this.id);
		this.setTransactions(transaction);
		this.setBalance(this.balance.add(amount));	
		System.out.println("Transaction of "+amount+" has been executed on Account "+this.getId()+ " .Current Balance is "+this.getBalance() );
	}
	
	public boolean withdraw(BigDecimal amount) {
		synchronized (this) {
			executeTransaction(amount.negate());
		}
		return true;
	}
	
	public boolean deposit(BigDecimal amount) {
		synchronized (this) {
			executeTransaction(amount);
		}
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Transaction transaction) {
		this.getTransactions().add(transaction);
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public static Account getAccount(int accountId) {
		return accountRepository.getAccount(accountId);
	}

	public static Map getAccounts() {
		return accountRepository.getAccounts();
	}

}