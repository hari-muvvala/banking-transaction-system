package com.atlas.banking;

import com.atlas.banking.model.Transaction;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class TransactionSmokeTest {
    public static void main(String[] args) {
        // Same region and credentials
        Region region = Region.AP_SOUTH_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        // IMPORTANT: table name must match my DynamoDB table for transactions
        String tableName = "Transactions";
        TransactionRepository repo = new TransactionRepository(ddb, tableName);

        // Create a sample transaction
        Transaction txn = new Transaction();
        txn.setTransactionId("txn-1001");
        txn.setAccountId("acc-1002");
        txn.setType("DEPOSIT");
        txn.setAmount(200.0);
        txn.setTimestamp(java.time.Instant.now().toString());

        // Save it
        repo.put(txn);
        System.out.println("Saved transaction: " + txn.getTransactionId());
    }
}