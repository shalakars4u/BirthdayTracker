package db.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBTable(tableName="birthdaytracker")
public class BirthdayTracker {
    @DynamoDBHashKey(attributeName="name")
    private String name;
    @DynamoDBAttribute(attributeName="dateOfBirth")
    private String dateOfBirth;
    @DynamoDBIndexHashKey(attributeName="month",globalSecondaryIndexName="month-name-index")
    private int month;
    @DynamoDBIndexHashKey(attributeName="date",globalSecondaryIndexName="date-name-index")
    private int date;
}
