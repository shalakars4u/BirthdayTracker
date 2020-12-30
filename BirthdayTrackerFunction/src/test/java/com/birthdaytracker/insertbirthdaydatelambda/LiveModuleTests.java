package com.birthdaytracker.insertbirthdaydatelambda;

import com.birthdaytracker.functions.InsertBirthdayDateLambda;
import com.birthdaytracker.module.LiveModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LiveModuleTests {

    @BeforeEach
    public void setup() throws Exception {

    }
    @Test
    public void createAndConfigureLiveModule() {
        LiveModule sut = new LiveModule();
        Injector injector = Guice.createInjector(sut);
        assertNotNull(injector);
    }

    @Test
    public void buildLambdasWithLiveModule() {
        InsertBirthdayDateLambda.setInjector(null);
        InsertBirthdayDateLambda insertBirthdayDateLambda = new InsertBirthdayDateLambda();
        assertNotNull(insertBirthdayDateLambda);

    }
}
