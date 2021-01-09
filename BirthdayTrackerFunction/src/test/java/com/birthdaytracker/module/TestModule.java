package com.birthdaytracker.module;

import com.birthdaytracker.ddb.client.BirthdayTrackerMappingDdbClient;
import com.birthdaytracker.factory.DbModelFactory;
import com.birthdaytracker.factory.GetDateOfBirthFactory;
import com.birthdaytracker.factory.ResponseFactory;
import com.birthdaytracker.model.GetDateOfBirthResult;
import com.google.inject.AbstractModule;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class TestModule extends AbstractModule {
    @Mock
    public BirthdayTrackerMappingDdbClient birthdayTrackerClient;
    @Mock
    public DbModelFactory dbModelFactory;
    @Mock
    public ResponseFactory response;
    @Mock
    public GetDateOfBirthFactory dateFactory;



    public TestModule() {

        initMocks(this);
    }

    @Override
    protected void configure() {
        bind(BirthdayTrackerMappingDdbClient.class).toInstance(birthdayTrackerClient);
        bind(DbModelFactory.class).toInstance(dbModelFactory);
        bind(ResponseFactory.class).toInstance(response);
        bind(GetDateOfBirthFactory.class).toInstance(dateFactory);
    }
}
