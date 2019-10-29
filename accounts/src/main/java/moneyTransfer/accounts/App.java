package moneyTransfer.accounts;

import static spark.Spark.get;
import static spark.Spark.post;

import java.math.BigDecimal;

import com.google.gson.Gson;

import moneyTransfer.accounts.entity.Account;
import moneyTransfer.accounts.entity.Transfer;
import moneyTransfer.accounts.service.AccountService;
import moneyTransfer.accounts.service.AccountServiceImpl;
import moneyTransfer.accounts.service.TransferService;
import moneyTransfer.accounts.service.TransferServiceImpl;
import moneyTransfer.accounts.utils.StandardResponse;
import moneyTransfer.accounts.utils.StatusResponse;

/**
 * Main App
 */
public class App {

	private final static AccountService accountService = new AccountServiceImpl();
	private final static TransferService transferService = new TransferServiceImpl();

	public static void main(String[] args) {

		post("/transfer", (request, response) -> {
			response.type("application/json");
			Transfer transfer = new Gson().fromJson(request.body(), Transfer.class);
			Account accountFrom = accountService.getAccount(transfer.getAccountFromId());
			Account accountTo = accountService.getAccount(transfer.getAccountToId());
			transferService.transfer(accountFrom, accountTo, transfer.getAmount());
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
		});

		post("/accounts", (request, response) -> {
			response.type("application/json");
			Account a = new Gson().fromJson(request.body(), Account.class);
			Account account = accountService.createAccount(a.getBalance());
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(account)));
		});

		get("/accounts", (request, response) -> {
			response.type("application/json");
			return new Gson().toJson(
					new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(accountService.getAccounts())));
		});

		get("/accounts/:id/transactions", (request, response) -> {
			response.type("application/json");
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson()
					.toJsonTree(accountService.getAccountTransactions(Integer.parseInt(request.params(":id"))))));
		});

		get("/accounts/:id", (request, response) -> {
			response.type("application/json");
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
					new Gson().toJsonTree(accountService.getAccount(Integer.parseInt(request.params(":id"))))));
		});

		get("/accounts/:id/balance", (request, response) -> {
			response.type("application/json");
			BigDecimal balance = null;
			try {
				balance = accountService.getAccountBalance(Integer.parseInt(request.params(":id")));

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (balance != null) {
				return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(balance)));
			} else {
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
						new Gson().toJson("Account not found or error in getting the balance")));
			}

		});
	}
}