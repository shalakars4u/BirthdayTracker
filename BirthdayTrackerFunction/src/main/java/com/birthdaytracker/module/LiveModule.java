package com.birthdaytracker.module;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.birthdaytracker.ddb.client.BirthdayTrackerMappingDdbClient;
import com.birthdaytracker.ddb.client.DdbClient;
import com.birthdaytracker.ddb.client.IddbClient;
import com.birthdaytracker.factory.DbModelFactory;
import com.birthdaytracker.factory.ParseFactory;
import com.birthdaytracker.utils.ForgivingObjectMapperFactory;
import com.birthdaytracker.utils.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;


public class LiveModule extends AbstractModule {
    AmazonDynamoDB client;

    @Override
    protected void configure() {
        ObjectMapper om = new ForgivingObjectMapperFactory().create();
        Json json = new Json(om);
        ParseFactory parseFactory = new ParseFactory();
        DbModelFactory dbModelFactory = new DbModelFactory(json, parseFactory);
        setupAwsClents();

        DynamoDBMapper mapper = new DynamoDBMapper(client);
        IddbClient ddbClient = new DdbClient(mapper);
        BirthdayTrackerMappingDdbClient birthdayTrackerClient =
                new BirthdayTrackerMappingDdbClient(ddbClient);

        bind(BirthdayTrackerMappingDdbClient.class).toInstance(birthdayTrackerClient);
        bind(DbModelFactory.class).toInstance(dbModelFactory);
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