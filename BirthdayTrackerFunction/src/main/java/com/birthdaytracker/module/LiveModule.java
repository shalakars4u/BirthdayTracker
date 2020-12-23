package com.birthdaytracker.module;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.birthdaytracker.ddb.client.BirthdayTrackerMappingDDBClient;
import com.birthdaytracker.ddb.client.DDBClient;
import com.birthdaytracker.ddb.client.IDDBClient;
import com.birthdaytracker.factory.DbModelFactory;
import com.birthdaytracker.utils.ForgivingObjectMapperFactory;
import com.birthdaytracker.utils.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;


public class LiveModule extends AbstractModule {
    @Override
    protected void configure() {
        ObjectMapper om = new ForgivingObjectMapperFactory().create();
        Json json = new Json(om);
        DbModelFactory dbModelFactory = new DbModelFactory(json);

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

        DynamoDBMapper mapper = new DynamoDBMapper(client);
        IDDBClient ddbClient = new DDBClient(mapper);
        BirthdayTrackerMappingDDBClient birthdayTrackerClient=new BirthdayTrackerMappingDDBClient(ddbClient);
        bind(BirthdayTrackerMappingDDBClient.class).toInstance(birthdayTrackerClient);
        bind(DbModelFactory.class).toInstance(dbModelFactory);


    }
}