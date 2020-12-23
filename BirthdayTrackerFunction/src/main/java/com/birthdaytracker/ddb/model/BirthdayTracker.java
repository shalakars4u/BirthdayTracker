package com.birthdaytracker.ddb.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBTable(tableName = "birthdaytracker")
public class BirthdayTracker {
    @DynamoDBHashKey(attributeName = "name")
    private String name;
    @DynamoDBAttribute(attributeName = "dateOfBirth")
    private String dateOfBirth;
    @DynamoDBIndexHashKey(attributeName = "month",
            globalSecondaryIndexName = "monthAndDateIndexName")
    private int month;
    @DynamoDBIndexRangeKey(attributeName = "date",
            globalSecondaryIndexName = "monthAndDateIndexName")
    private int date;
}
