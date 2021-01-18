package com.birthdaytracker.ddb.client;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class DdbClient implements IddbClient {
    @NonNull
    private final DynamoDBMapper dynamoDBMapper;


    @Override
    public void saveItem(Object item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("Attempt to save null data");

        }
        dynamoDBMapper.save(item);
    }

    @Override
    public void deleteItem(Object item) {
        dynamoDBMapper.delete(item);
    }

    public <T> T loadItem(String hashKey, Class<T> type) {
        return dynamoDBMapper.load(type, hashKey);

    }

    @Override
    public List<BirthdayTracker> retrieveItem( DynamoDBQueryExpression<BirthdayTracker> expr) {

        List<BirthdayTracker> result = dynamoDBMapper
                .query(BirthdayTracker.class, expr);

        return result;

    }
}
