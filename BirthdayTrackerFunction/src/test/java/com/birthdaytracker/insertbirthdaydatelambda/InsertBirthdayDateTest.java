package com.birthdaytracker.insertbirthdaydatelambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.birthdaytracker.ddb.client.BirthdayTrackerMappingDdbClient;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.birthdaytracker.factory.DbModelFactory;
import com.birthdaytracker.functions.InsertBirthdayDateLambda;
import com.google.inject.Guice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class InsertBirthdayDateTest {

    @Mock
    private APIGatewayProxyResponseEvent responseMock;
    @Mock
    private APIGatewayProxyRequestEvent input;
    @Mock
    private BirthdayTracker birthdayTracker;

    private Context context;
    private TestModule di;
    private InsertBirthdayDateLambda sut;

    @BeforeEach
    public void setup() {
        initMocks(this);
        di = new TestModule();
        when(di.dbModelFactory.create(input)).thenReturn(birthdayTracker);
        when(responseMock.getStatusCode()).thenReturn(200);
        context = null;

        InsertBirthdayDateLambda.setInjector(Guice.createInjector(di));
        sut = new InsertBirthdayDateLambda();

    }

    @Test
    public void constructor_AnyArgsNull_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new InsertBirthdayDateLambda(null, di.birthdayTrackerClient));
        assertThrows(IllegalArgumentException.class, () -> new InsertBirthdayDateLambda(di.dbModelFactory, null));
    }

    @Test
    public void handleRequest_AnyThrows_DoesNotCatch() {

        doThrow(new NullPointerException("Exception")).when(di.dbModelFactory).create(input);
        APIGatewayProxyResponseEvent result =  sut.handleRequest(input,context);
        assertEquals(500, result.getStatusCode());
    }

    @Test
    public void handleRequest_NoThrow_ReturnGeneratedResponse() throws Exception {
        APIGatewayProxyResponseEvent result = sut.handleRequest(input, context);
        verify(di.birthdayTrackerClient,times(1)).saveNameDateRequestMapping(birthdayTracker);
        assertEquals(responseMock.getStatusCode(), result.getStatusCode());
        assertEquals("SUCCESS", result.getBody().toString());

    }
}
