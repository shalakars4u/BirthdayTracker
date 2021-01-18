package com.birthdaytracker.factory;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.birthdaytracker.model.InsertDateRequest;
import com.birthdaytracker.utils.Json;
import com.birthdaytracker.utils.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DbModelFactoryTests {

    @Mock
    InsertDateRequest dateRequest;
    @Mock
    APIGatewayProxyRequestEvent event;
    @Mock
    private DbModelFactory sut;
    @Mock
    private Json json;
    @Mock
    private ParseFactory parseFactory;


    @BeforeEach
    public void setup() {

        initMocks(this);
        when(event.getBody()).thenReturn(TestConstants.input);
        when(json.deserialize(InsertDateRequest.class, event.getBody())).thenReturn(dateRequest);
        when(dateRequest.getName()).thenReturn(TestConstants.name);
        when(dateRequest.getDateOfBirth()).thenReturn(TestConstants.dateOfBirth);
        when(parseFactory.create(dateRequest.getDateOfBirth(), "month")).thenReturn(TestConstants.month);
        when(parseFactory.create(dateRequest.getDateOfBirth(), "date")).thenReturn(TestConstants.date);
        sut = new DbModelFactory(json, parseFactory);
    }

    @Test
    public void create_NoThrow_ReturnHostMapWithComponents() {
        BirthdayTracker result = sut.create(event);
        assertEquals(TestConstants.name, result.getName());
        assertEquals(TestConstants.dateOfBirth, result.getDateOfBirth());
        assertEquals(TestConstants.month, result.getMonth());
        assertEquals(TestConstants.date, result.getDate());
    }

    @Test
    public void create_AnyThrows_DoesNotCatch() {
        when(dateRequest.getName()).thenThrow(new NullPointerException(TestConstants.EXPECTED_EXCEPTION_MESSAGE));
        assertThrows(NullPointerException.class, () -> sut.create(event));
    }

    @Test
    public void create_AnyThrow_DoesNotCatch() {
        when(dateRequest.getDateOfBirth()).thenThrow(new NullPointerException(TestConstants.EXPECTED_EXCEPTION_MESSAGE));
        assertThrows(NullPointerException.class, () -> sut.create(event));
    }

    @Test
    public void create_With_MonthDate() {
        BirthdayTracker result = sut.create(TestConstants.month,TestConstants.date);
        assertEquals(TestConstants.month, result.getMonth());
        assertEquals(TestConstants.date, result.getDate());
    }
}
