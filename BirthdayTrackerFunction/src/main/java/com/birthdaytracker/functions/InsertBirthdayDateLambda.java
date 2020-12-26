package com.birthdaytracker.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.birthdaytracker.ddb.client.BirthdayTrackerMappingDdbClient;
import com.birthdaytracker.factory.DbModelFactory;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import lombok.NonNull;
import org.apache.http.util.Args;

import java.util.HashMap;
import java.util.Map;


/**
 * Handler for requests to Lambda function.
 */

public class InsertBirthdayDateLambda extends BaseInjectedLambda implements
        RequestHandler<APIGatewayProxyRequestEvent,
        APIGatewayProxyResponseEvent> {
    /**
     * API Gateway function.
     */
    @NonNull
    DbModelFactory dbModelFactory;
    @NonNull
    BirthdayTrackerMappingDdbClient mapper;

    public InsertBirthdayDateLambda() {
        this(getInjector().getInstance(DbModelFactory.class),
                getInjector().getInstance(BirthdayTrackerMappingDdbClient.class));
    }

    public InsertBirthdayDateLambda(final DbModelFactory dbModelFactory,
                                    final BirthdayTrackerMappingDdbClient mapper) {
        this.dbModelFactory = Args.notNull(dbModelFactory, "dbModelFactory");
        this.mapper = Args.notNull(mapper, "mappper");
    }

    /**
     * handleRequest.
     */
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        input.getBody();

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        try {
            BirthdayTracker birthdayTracker = dbModelFactory.create(input);
            mapper.saveNameDateRequestMapping(birthdayTracker);
            return response.withBody("SUCCESS").withStatusCode(200);
        } catch (Exception e) {
            return response
                    .withBody("{}")
                    .withStatusCode(500);
        }
    }
}
