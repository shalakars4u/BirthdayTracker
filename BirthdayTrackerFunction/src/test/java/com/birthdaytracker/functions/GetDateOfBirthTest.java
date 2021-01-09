package com.birthdaytracker.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.birthdaytracker.model.GetDateOfBirthResult;
import com.birthdaytracker.module.TestModule;
import com.birthdaytracker.utils.TestConstants;
import com.google.inject.Guice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetDateOfBirthTest {
    @Mock
    private APIGatewayProxyRequestEvent input;
    @Mock
    private BirthdayTracker birthdayTracker;
    public GetDateOfBirthResult result;
    @Mock
    Map<String, String> pathParameters;
    private Context context;
    private TestModule di;
    private GetDateOfBirthLambda sut;


    @BeforeEach
    public void setup() {
        initMocks(this);
        di = new TestModule();
        when(input.getPathParameters()).thenReturn(pathParameters);
        when(pathParameters.get("name")).thenReturn(TestConstants.name);
        when(di.birthdayTrackerClient.getDateOfBirthRequestMapping(TestConstants.name)).thenReturn(birthdayTracker);
        result = GetDateOfBirthResult.builder().dateOfBirth(TestConstants.dateOfBirth).name(TestConstants.name).age(TestConstants.age).build();
        when(di.dateFactory.retrieveDetails(birthdayTracker)).thenReturn(result);

        String output = "The person " + result.getName()
                + " has "
                + result.getDateOfBirth()
                + " date of birth and the age is " + result.getAge();
        String expected = "The person " + TestConstants.name
                + " has "
                + TestConstants.dateOfBirth
                + " date of birth and the age is " + TestConstants.age;
        when(di.response.create(expected,200)).thenReturn(new APIGatewayProxyResponseEvent().withBody(output).withStatusCode(200));
        when(di.response.create("FAILURE",500)).thenReturn(new APIGatewayProxyResponseEvent().withBody("FAILURE").withStatusCode(500));
        context = null;

        GetDateOfBirthLambda.setInjector(Guice.createInjector(di));
        sut = new GetDateOfBirthLambda();

    }

    @Test
    public void constructor_AnyArgsNull_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new GetDateOfBirthLambda(null,di.response,di.dateFactory));
        assertThrows(IllegalArgumentException.class, () -> new GetDateOfBirthLambda(di.birthdayTrackerClient,null,di.dateFactory));
        assertThrows(IllegalArgumentException.class, () -> new GetDateOfBirthLambda(di.birthdayTrackerClient,di.response,null));
    }

    @Test
    public void handleRequest_AnyThrows_DoesNotCatch() {

        doThrow(new NullPointerException("Exception")).when(di.birthdayTrackerClient)
                .getDateOfBirthRequestMapping(TestConstants.name);
        APIGatewayProxyResponseEvent result =  sut.handleRequest(input,context);
        assertEquals(500, result.getStatusCode());
        assertEquals("FAILURE", result.getBody());

    }

    @Test
    public void handleRequest_NoThrow_ReturnGeneratedResponse() throws Exception {
        APIGatewayProxyResponseEvent result = sut.handleRequest(input, context);
        String expected = "The person " + TestConstants.name
                + " has "
                + TestConstants.dateOfBirth
                + " date of birth and the age is " + TestConstants.age;
       assertEquals(expected, result.getBody().toString());
       assertEquals(200, result.getStatusCode());
    }
}
