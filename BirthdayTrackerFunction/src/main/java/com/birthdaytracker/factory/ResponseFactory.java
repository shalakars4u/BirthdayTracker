package com.birthdaytracker.factory;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class ResponseFactory {

    @NonNull private Map<String, String> headers;

    /**
     * Create.
     */
    public APIGatewayProxyResponseEvent create(String body, Integer statusCode) {
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        return new APIGatewayProxyResponseEvent().withHeaders(headers)
                .withBody(body)
                .withStatusCode(statusCode);
    }


}
