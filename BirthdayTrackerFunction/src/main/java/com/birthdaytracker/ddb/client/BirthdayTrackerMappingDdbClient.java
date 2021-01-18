package com.birthdaytracker.ddb.client;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.birthdaytracker.factory.DbExpressionFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.util.List;


@RequiredArgsConstructor
public class BirthdayTrackerMappingDdbClient {

    @NonNull
    private IddbClient ddbClient;
    @NonNull
    private DbExpressionFactory factory;

    public void saveNameDateRequestMapping(BirthdayTracker bd) {
        ddbClient.saveItem(bd);

    }

    public BirthdayTracker getDateOfBirthRequestMapping(String name) {
        return ddbClient.loadItem(name, BirthdayTracker.class);
    }

    public List<BirthdayTracker> retrieveNamesPerMonth(BirthdayTracker bd) {
        DynamoDBQueryExpression<BirthdayTracker> expr = factory.retrieve(bd);
        return ddbClient.retrieveItem(expr);
    }

}
