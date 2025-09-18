package com.atlas.banking;

import com.atlas.banking.model.Account;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class AccountSmokeTest {
    public static void main(String[] args) {
        // Using the same region as in AWS Console (Mumbai)
        Region region = Region.AP_SOUTH_1;

        // Creating DynamoDB client (picks up credentials from environment)
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        // Repository to interact with DynamoDB table
        String tableName = "Accounts";
        AccountRepository repo = new AccountRepository(ddb, tableName);

        // Service to perform deposit and withdrawal operations
        AccountService service = new AccountService(repo);

        // Create a sample account
        Account a = new Account();
        a.setAccountId("acc-1002");
        a.setAccountHolderName("Test User");
        a.setBalance(500.0);

        // Save the account
        repo.put(a);
        System.out.println("Saved account: " + a.getAccountId());

        // Read the account back
        Account loaded = repo.get("acc-1002");
        if (loaded != null) {
            System.out.println("Loaded: " + loaded.getAccountId()
                    + " | " + loaded.getAccountHolderName()
                    + " | balance=" + loaded.getBalance());
        } else {
            System.out.println("Account not found.");
        }

        // --- NEW PART: Deposit and Withdraw using AccountService ---

        // Deposit 200 into the account
        service.deposit("acc-1002", 200.0);

        // Withdraw 50 from the account
        service.withdraw("acc-1002", 50.0);

        // Read the account again to verify balance updates
        Account updated = repo.get("acc-1002");
        if (updated != null) {
            System.out.println("After transactions: " + updated.getAccountId()
                    + " | " + updated.getAccountHolderName()
                    + " | balance=" + updated.getBalance());
        }
    }
}
