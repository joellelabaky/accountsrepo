package moneyTransfer.accounts.repository;

import java.util.concurrent.ConcurrentHashMap;

import moneyTransfer.accounts.entity.Transaction;

public class TransactionRepository {

	private static ConcurrentHashMap<Integer, Transaction> transactions = new ConcurrentHashMap<>();

	public void addTransaction(int id, Transaction transaction) {
		transactions.put(id, transaction);	
	}
	
	public Transaction getTransaction(int id) {
		return transactions.get(id);	
	}

}
