package com.atlas.banking;

import com.atlas.banking.model.Account;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class AccountSmokeTest {
    public static void main(String[] args) {
        // I use the same region I used in AWS Console (Mumbai)
        Region region = Region.AP_SOUTH_1;

        // This client uses my current AWS credentials (from MakeMyLabs export)
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        // IMPORTANT: table name must match exactly what I created
        String tableName = "Accounts";
        AccountRepository repo = new AccountRepository(ddb, tableName);

        // Create a sample account
        Account a = new Account();
        a.setAccountId("acc-1002");
        a.setAccountHolderName("Test User");
        a.setBalance(500.0);

        // Save it
        repo.put(a);
        System.out.println("Saved account: " + a.getAccountId());

        // Read it back
        Account loaded = repo.get("acc-1002");
        if (loaded != null) {
            System.out.println("Loaded: " + loaded.getAccountId()
                    + " | " + loaded.getAccountHolderName()
                    + " | balance=" + loaded.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }
}