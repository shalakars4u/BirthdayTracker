package com.birthdaytracker.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.birthdaytracker.factory.DbModelFactory;
import com.birthdaytracker.model.GetDateOfBirthResult;
import com.birthdaytracker.module.TestModule;
import com.birthdaytracker.utils.Json;
import com.birthdaytracker.utils.TestConstants;
import com.google.inject.Guice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetNamesPerMonthTest {
    @Mock
    private APIGatewayProxyRequestEvent input;

    private BirthdayTracker birthdayTracker;
    @Mock
    Map<String, String> pathParameters;
    private Context context;
    private TestModule di;
    private GetNamesPerMonthLambda sut;
    @Mock
    BirthdayTracker bd1;
    @Mock
    BirthdayTracker bd2;
    @Mock
    APIGatewayProxyResponseEvent responseEventSuccess;
    @Mock
    APIGatewayProxyResponseEvent responseEventFailure;

    private List<BirthdayTracker> birthdayList = Arrays.asList(bd1,bd2);

    private String result = "RESULT";

    @BeforeEach
    public void setup() {
        initMocks(this);
        di = new TestModule();
        when(input.getPathParameters()).thenReturn(pathParameters);
        when(pathParameters.get("month")).thenReturn(String.valueOf(TestConstants.month));
        when(pathParameters.get("date")).thenReturn(String.valueOf(TestConstants.date));
        birthdayTracker = BirthdayTracker.builder().name(TestConstants.name).month(TestConstants.month).date(TestConstants.date).build();
        when(di.dbModelFactory.create(TestConstants.month, TestConstants.date)).thenReturn(birthdayTracker);
        when(di.birthdayTrackerClient.retrieveNamesPerMonth(birthdayTracker)).thenReturn(birthdayList);
        when(di.json.serialize(birthdayList)).thenReturn(result);
        when(di.response.create(result,200)).thenReturn(responseEventSuccess);
        when(responseEventSuccess.getBody()).thenReturn(result);
        when(responseEventSuccess.getStatusCode()).thenReturn(200);

        when(di.response.create("FAILURE",500)).thenReturn(responseEventFailure);
        when(responseEventFailure.getBody()).thenReturn("FAILURE");
        when(responseEventFailure.getStatusCode()).thenReturn(500);


        context = null;

        GetNamesPerMonthLambda.setInjector(Guice.createInjector(di));
        sut = new GetNamesPerMonthLambda();

    }

    @Test
    public void constructor_AnyArgsNull_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new GetNamesPerMonthLambda(null, di.json, di.dbModelFactory, di.response));
        assertThrows(IllegalArgumentException.class, () -> new GetNamesPerMonthLambda(di.birthdayTrackerClient, null, di.dbModelFactory, di.response));
        assertThrows(IllegalArgumentException.class, () -> new GetNamesPerMonthLambda(di.birthdayTrackerClient, di.json, null, di.response));
        assertThrows(IllegalArgumentException.class, () -> new GetNamesPerMonthLambda(di.birthdayTrackerClient, di.json, di.dbModelFactory, null));
    }

    @Test
    public void handleRequest_AnyThrows_DoesNotCatch() {

        doThrow(new NullPointerException("Exception")).when(di.birthdayTrackerClient)
                .retrieveNamesPerMonth(birthdayTracker);
        APIGatewayProxyResponseEvent output = sut.handleRequest(input, context);
        assertEquals(500, output.getStatusCode());
        assertEquals("FAILURE", output.getBody());

    }

    @Test
    public void handleRequest_NoThrow_ReturnGeneratedResponse() throws Exception {
        APIGatewayProxyResponseEvent output = sut.handleRequest(input, context);
        System.out.println("output is" + output.getBody());
//        assertEquals(true, output.getBody().contains(TestConstants.name));
        assertEquals(result, output.getBody() );
        assertEquals(200, output.getStatusCode());
    }
}
