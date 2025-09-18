package com.atlas.banking;

import com.atlas.banking.model.Account;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class AccountSmokeTest {
    public static void main(String[] args) {
        // Region used in AWS Console (Mumbai)
        Region region = Region.AP_SOUTH_1;

        // Uses env credentials from IntelliJ Run Config
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        // Table names (must match Console)
        String accountsTable = "Accounts";
        String transactionsTable = "Transactions";

        AccountRepository accountRepo = new AccountRepository(ddb, accountsTable);
        TransactionRepository txnRepo = new TransactionRepository(ddb, transactionsTable);
        AccountService svc = new AccountService(accountRepo, txnRepo);

        // Create a sample account (idempotent overwrite is fine for MVP)
        Account a = new Account();
        a.setAccountId("acc-1002");
        a.setAccountHolderName("Test User");
        a.setBalance(500.0);
        accountRepo.put(a);
        System.out.println("Saved account: " + a.getAccountId());

        // Read once
        Account loaded = accountRepo.get("acc-1002");
        if (loaded != null) {
            System.out.println("Loaded: " + loaded.getAccountId()
                    + " | " + loaded.getAccountHolderName()
                    + " | balance=" + loaded.getBalance());
        }

        // Do a couple of transactions
        svc.deposit("acc-1002", 200.0);
        svc.withdraw("acc-1002", 50.0);

        // Read again to verify new balance
        Account after = accountRepo.get("acc-1002");
        if (after != null) {
            System.out.println("After transactions: " + after.getAccountId()
                    + " | " + after.getAccountHolderName()
                    + " | balance=" + after.getBalance());
        }
    }
}