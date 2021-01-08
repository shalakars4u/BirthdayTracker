package com.birthdaytracker.factory;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class ResponseFactoryTests {
    @Mock
    ResponseFactory response;

    APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
    Map<String, String> headers;
    @BeforeEach
    public void setup() {
        initMocks(this);
        headers=new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
    }
    @Test
    public void createResponse(){
        when(response.create("SUCCESS",200)).thenReturn(responseEvent.withHeaders(headers)
                .withBody("SUCCESS")
                .withStatusCode(200));
        when(response.create("FAILURE",500)).thenReturn(responseEvent.withHeaders(headers)
                .withBody("FAILURE")
                .withStatusCode(500));
    }
}
