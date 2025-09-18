package com.atlas.banking;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoPing {
    public static void main(String[] args) {
        // Explicitly set the AWS region (using the same region I used in AWS Console, e.g., ap-south-1)
        Region region = Region.AP_SOUTH_1;

        // Creating a DynamoDB client (this will automatically use my exported AWS credentials)
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        // If this runs without exception, connection works
        System.out.println("DynamoDB client created successfully in region: " + region);
    }
}