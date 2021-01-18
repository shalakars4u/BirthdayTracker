package com.birthdaytracker.factory;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.amazonaws.services.dynamodbv2.model.Condition;

public class DbExpressionFactory {
    /**
     * Retrieve.
     */
    public DynamoDBQueryExpression<BirthdayTracker> retrieve(BirthdayTracker birthdayTracker) {
        Condition rangeKeyCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue()
                        .withN(String.valueOf(birthdayTracker.getDate())));

        DynamoDBQueryExpression<BirthdayTracker> expr = new
                DynamoDBQueryExpression<BirthdayTracker>()
                .withHashKeyValues(birthdayTracker)
                .withIndexName("monthAndDateIndexName")
                .withRangeKeyCondition("date",
                        rangeKeyCondition)
                .withConsistentRead(false);
        return expr;
    }
}
