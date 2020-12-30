package com.birthdaytracker.insertbirthdaydatelambda;

import com.birthdaytracker.ddb.client.BirthdayTrackerMappingDdbClient;
import com.birthdaytracker.factory.DbModelFactory;
import com.google.inject.AbstractModule;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class TestModule extends AbstractModule {
    @Mock
    public BirthdayTrackerMappingDdbClient birthdayTrackerClient;
    @Mock
    public DbModelFactory dbModelFactory;

    public TestModule() {

        initMocks(this);
    }

    @Override
    protected void configure() {
        bind(BirthdayTrackerMappingDdbClient.class).toInstance(birthdayTrackerClient);
        bind(DbModelFactory.class).toInstance(dbModelFactory);
    }
}
