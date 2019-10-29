package moneyTransfer.accounts;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import moneyTransfer.accounts.service.AccountService;
import moneyTransfer.accounts.service.AccountServiceImpl;

/**
 * Unit test for simple Transfer App.
 */
public class AppTest extends TestCase {

	private final static AccountService accountService = new AccountServiceImpl();

	private static String postCreateAccountUrl = "http://localhost:4567/accounts";
	private static String postTransferUrl = "http://localhost:4567/transfer";

	public AppTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	public void testApp() throws Exception {

		App.main(new String[] {});
		// Create 4 accounts
		for (int i = 1; i < 5; i++) {
			HttpPost httpPost = new HttpPost(postCreateAccountUrl);
			httpPost.setEntity(new StringEntity("{ 'balance': '1000'}"));
			HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPost);

			assertEquals(200, httpResponse.getStatusLine().getStatusCode());
		}

		StringEntity transferPayloadFromA1ToA2 = new StringEntity(
				"{'accountFromId': '1','accountToId': '2', 'amount': '100'}");
		StringEntity transferPayloadFromA2ToA1 = new StringEntity(
				"{'accountFromId': '2','accountToId': '1', 'amount': '100'}");
		StringEntity transferPayloadFromA3ToA1 = new StringEntity(
				"{'accountFromId': '3','accountToId': '1', 'amount': '100'}");
		StringEntity transferPayloadFromA3ToA4 = new StringEntity(
				"{'accountFromId': '3','accountToId': '4', 'amount': '100'}");

		// Make concurrent transfer between acccounts
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				HttpPost httpPost = new HttpPost(postTransferUrl);

				try {

					httpPost.setEntity(transferPayloadFromA1ToA2);
					HttpClientBuilder.create().build().execute(httpPost);
					HttpClientBuilder.create().build().execute(httpPost);

					httpPost.setEntity(transferPayloadFromA2ToA1);
					HttpClientBuilder.create().build().execute(httpPost);
					HttpClientBuilder.create().build().execute(httpPost);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				HttpPost httpPost = new HttpPost(postTransferUrl);
				try {
					httpPost.setEntity(transferPayloadFromA2ToA1);
					HttpClientBuilder.create().build().execute(httpPost);
					HttpClientBuilder.create().build().execute(httpPost);
					httpPost.setEntity(transferPayloadFromA1ToA2);
					HttpClientBuilder.create().build().execute(httpPost);
					HttpClientBuilder.create().build().execute(httpPost);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t3 = new Thread(new Runnable() {
			public void run() {
				HttpPost httpPost = new HttpPost(postTransferUrl);
				try {
					httpPost.setEntity(transferPayloadFromA3ToA1);
					HttpClientBuilder.create().build().execute(httpPost);
					HttpClientBuilder.create().build().execute(httpPost);
					httpPost.setEntity(transferPayloadFromA3ToA4);
					HttpClientBuilder.create().build().execute(httpPost);
					HttpClientBuilder.create().build().execute(httpPost);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t4 = new Thread(new Runnable() {
			public void run() {
				HttpPost httpPost = new HttpPost(postTransferUrl);
				try {
					httpPost.setEntity(transferPayloadFromA2ToA1);
					HttpClientBuilder.create().build().execute(httpPost);
					HttpClientBuilder.create().build().execute(httpPost);
					httpPost.setEntity(transferPayloadFromA1ToA2);
					HttpClientBuilder.create().build().execute(httpPost);
					HttpClientBuilder.create().build().execute(httpPost);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t1.join();
		t2.join();
		t3.join();
		t4.join();

		assertEquals(accountService.getAccountBalance(1).toString(), "1200");
		assertEquals(accountService.getAccountBalance(2).toString(), "1000");
		assertEquals(accountService.getAccountBalance(3).toString(), "600");
		assertEquals(accountService.getAccountBalance(4).toString(), "1200");

	}
}
