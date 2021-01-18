package com.birthdaytracker.factory;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class DbExpressionFactoryTests {

    @Mock
    private DbExpressionFactory sut;

    @BeforeEach
    public void setup() {

        initMocks(this);

        sut = new DbExpressionFactory();
    }


}
