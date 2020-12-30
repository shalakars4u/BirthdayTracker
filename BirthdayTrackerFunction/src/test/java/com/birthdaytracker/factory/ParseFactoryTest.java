package com.birthdaytracker.factory;

import com.birthdaytracker.model.InsertDateRequest;
import com.birthdaytracker.utils.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ParseFactoryTest {
    @Mock
    InsertDateRequest dateRequest;
    @Mock
    private ParseFactory parseFactory;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void create_ParseFactory_Test(){
        when(dateRequest.getDateOfBirth()).thenReturn(TestConstants.dateOfBirth);
        when(parseFactory.create(dateRequest.getDateOfBirth(), "month")).thenReturn(TestConstants.month);
        when(parseFactory.create(dateRequest.getDateOfBirth(), "date")).thenReturn(TestConstants.date);
    }

}
