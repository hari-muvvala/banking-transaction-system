package com.atlas.banking.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Transaction {

    private String transactionId;   // PK
    private String accountId;       // FK to Accounts.accountId (stored as attribute)
    private String type;            // "DEPOSIT" | "WITHDRAW"
    private Double amount;          // use Double for SDK bean compatibility
    private String status;          // "SUCCESS" | "FAILED" (MVP)
    private String timestamp;       // ISO-8601 string

    // ---- PK ----
    @DynamoDbPartitionKey
    @DynamoDbAttribute("transactionId")
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    // ---- Attributes ----
    @DynamoDbAttribute("accountId")
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    @DynamoDbAttribute("type")
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @DynamoDbAttribute("amount")
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    @DynamoDbAttribute("status")
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @DynamoDbAttribute("timestamp")
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}