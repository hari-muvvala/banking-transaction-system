package com.atlas.banking;

import com.atlas.banking.model.Transaction;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class TransactionRepository {
    private final DynamoDbTable<Transaction> table;

    public TransactionRepository(DynamoDbClient ddb, String tableName) {
        DynamoDbEnhancedClient enhanced = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
        this.table = enhanced.table(tableName, TableSchema.fromBean(Transaction.class));
    }

    // Save a new transaction
    public void put(Transaction txn) {
        table.putItem(txn);
    }
}