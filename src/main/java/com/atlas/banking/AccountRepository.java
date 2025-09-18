package com.atlas.banking;

import com.atlas.banking.model.Account;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class AccountRepository {
    private final DynamoDbTable<Account> table;

    public AccountRepository(DynamoDbClient ddb, String tableName) {
        DynamoDbEnhancedClient enhanced = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
        this.table = enhanced.table(tableName, TableSchema.fromBean(Account.class));
    }

    //Create or replace an account
    public void put(Account account) {
        table.putItem(account);
    }

    // Load by accountId (partition key)
    public Account get(String accountId) {
        Key key = Key.builder().partitionValue(accountId).build();
        return table.getItem(GetItemEnhancedRequest.builder().key(key).build());
    }
}