package com.birthdaytracker.factory;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.birthdaytracker.model.GetDateOfBirthResult;
import com.birthdaytracker.module.TestModule;
import com.birthdaytracker.utils.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetDateOfBirthFactoryTests {
    @Mock
    private APIGatewayProxyRequestEvent input;
    @Mock
    private ParseFactory parseFactory;
    @Mock
    private GetDateOfBirthFactory sut;
    @Mock
    private GetDateOfBirthResult result;
    @Mock
    private BirthdayTracker birthdayTracker;

    private TestModule di;


    @BeforeEach
    public void setup() {

        initMocks(this);
        di = new TestModule();
        when(di.birthdayTrackerClient.getDateOfBirthRequestMapping(TestConstants.name)).thenReturn(birthdayTracker);
        when(birthdayTracker.getName()).thenReturn(TestConstants.name);
        when(birthdayTracker.getDateOfBirth()).thenReturn(TestConstants.dateOfBirth);
        when(parseFactory.create(TestConstants.dateOfBirth, "month")).thenReturn(TestConstants.month);
        when(parseFactory.create(TestConstants.dateOfBirth, "year")).thenReturn(TestConstants.year);
        when(parseFactory.create(TestConstants.dateOfBirth, "date")).thenReturn(TestConstants.date);
        when(di.dateFactory.retrieveAge(birthdayTracker.getDateOfBirth())).thenReturn(TestConstants.age);
        when(di.dateFactory.retrieveDetails(birthdayTracker)).thenReturn(result);
        sut = new GetDateOfBirthFactory(parseFactory);
    }

    @Test
    public void create_NoThrow_ReturnHostMapWithComponents() {
        GetDateOfBirthResult result = sut.retrieveDetails(birthdayTracker);
        assertEquals(TestConstants.name, result.getName());
        assertEquals(TestConstants.dateOfBirth, result.getDateOfBirth());
        assertEquals(TestConstants.age, result.getAge());
    }

    @Test
    public void create_AnyThrows_DoesNotCatch() {
        when(birthdayTracker.getName()).thenThrow(new NullPointerException(TestConstants.EXPECTED_EXCEPTION_MESSAGE));
        assertThrows(NullPointerException.class, () -> sut.retrieveDetails(birthdayTracker));
    }

    @Test
    public void create_AnyThrow_DoesNotCatch() {
        when(birthdayTracker.getDateOfBirth()).thenThrow(new NullPointerException(TestConstants.EXPECTED_EXCEPTION_MESSAGE));
        assertThrows(NullPointerException.class, () -> sut.retrieveDetails(birthdayTracker));
    }

}
