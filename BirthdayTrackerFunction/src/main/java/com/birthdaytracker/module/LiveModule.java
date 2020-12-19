package com.birthdaytracker.module;

import com.birthdaytracker.utils.ForgivingObjectMapperFactory;
import com.birthdaytracker.utils.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;


public class LiveModule extends AbstractModule {
    @Override
    protected void configure() {
        ObjectMapper om = new ForgivingObjectMapperFactory().create();
        Json json = new Json(om);


    }
}