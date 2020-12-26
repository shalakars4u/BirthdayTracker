package com.birthdaytracker.functions;

import com.birthdaytracker.module.LiveModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.Synchronized;

public class BaseInjectedLambda {
    private static Injector injector;

    @Synchronized
    protected static Injector getInjector() {
        if (injector == null) {
            injector = Guice.createInjector(new LiveModule());
        }
        return injector;
    }

    @Synchronized
    public static void setInjector(final Injector injector) {
        BaseInjectedLambda.injector = injector;
    }

    protected BaseInjectedLambda() {

    }
}

