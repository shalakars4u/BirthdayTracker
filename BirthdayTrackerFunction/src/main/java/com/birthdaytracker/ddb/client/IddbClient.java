package com.birthdaytracker.ddb.client;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.birthdaytracker.ddb.model.BirthdayTracker;

import java.util.List;

public interface IddbClient {

    void deleteItem(Object item);

    void saveItem(Object item);

    <T> T loadItem(String hashKey, Class<T> type);

    List<BirthdayTracker> retrieveItem( DynamoDBQueryExpression<BirthdayTracker> expr);
}
