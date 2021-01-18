package com.birthdaytracker.module;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.birthdaytracker.ddb.client.BirthdayTrackerMappingDdbClient;
import com.birthdaytracker.ddb.client.DdbClient;
import com.birthdaytracker.ddb.client.IddbClient;
import com.birthdaytracker.factory.DbExpressionFactory;
import com.birthdaytracker.factory.DbModelFactory;
import com.birthdaytracker.factory.ParseFactory;
import com.birthdaytracker.factory.ResponseFactory;
import com.birthdaytracker.factory.GetDateOfBirthFactory;
import com.birthdaytracker.utils.ForgivingObjectMapperFactory;
import com.birthdaytracker.utils.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;

import java.util.HashMap;
import java.util.Map;


public class LiveModule extends AbstractModule {
    AmazonDynamoDB client;

    @Override
    protected void configure() {
        ObjectMapper om = new ForgivingObjectMapperFactory().create();
        Json json = new Json(om);
        ParseFactory parseFactory = new ParseFactory();
        Map<String, String> headers = new HashMap<>();

        DbModelFactory dbModelFactory = new DbModelFactory(json, parseFactory);
        final ResponseFactory response = new ResponseFactory(headers);
        final GetDateOfBirthFactory dateFactory = new GetDateOfBirthFactory(parseFactory);
        setupAwsClents();

        DynamoDBMapper mapper = new DynamoDBMapper(client);
        IddbClient ddbClient = new DdbClient(mapper);
        DbExpressionFactory factory = new DbExpressionFactory();
        BirthdayTrackerMappingDdbClient birthdayTrackerClient =
                new BirthdayTrackerMappingDdbClient(ddbClient, factory);
        bind(BirthdayTrackerMappingDdbClient.class).toInstance(birthdayTrackerClient);
        bind(DbModelFactory.class).toInstance(dbModelFactory);
        bind(ResponseFactory.class).toInstance(response);
        bind(GetDateOfBirthFactory.class).toInstance(dateFactory);

    }

    /**
     * setupAwsClents.
     */
    public void setupAwsClents() {
        try {
            client = AmazonDynamoDBClientBuilder.standard().build();
        } catch (Exception e) {
            client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

        }

    }
}