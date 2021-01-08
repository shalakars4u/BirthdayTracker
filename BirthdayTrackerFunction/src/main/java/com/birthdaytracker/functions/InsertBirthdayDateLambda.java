package com.birthdaytracker.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.birthdaytracker.ddb.client.BirthdayTrackerMappingDdbClient;
import com.birthdaytracker.factory.DbModelFactory;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.birthdaytracker.factory.ResponseFactory;
import lombok.NonNull;
import org.apache.http.util.Args;

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
    @NonNull
    ResponseFactory response;

    /**
     * InsertBirthdayDateLambda.
     */

    public InsertBirthdayDateLambda() {
        this(getInjector().getInstance(DbModelFactory.class),
                getInjector().getInstance(BirthdayTrackerMappingDdbClient.class),
                getInjector().getInstance(ResponseFactory.class));
    }

    /**
     * InsertBirthdayDateLambda.
     */

    public InsertBirthdayDateLambda(final DbModelFactory dbModelFactory,
                                    final BirthdayTrackerMappingDdbClient mapper,
                                    final ResponseFactory response ) {
        this.dbModelFactory = Args.notNull(dbModelFactory, "dbModelFactory");
        this.mapper = Args.notNull(mapper, "mapper");
        this.response = Args.notNull(response,"response");
    }

    /**
     * handleRequest.
     */
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {

        input.getBody();

        try {
            BirthdayTracker birthdayTracker = dbModelFactory.create(input);
            mapper.saveNameDateRequestMapping(birthdayTracker);
            return response.create("SUCCESS",200);
        } catch (Exception e) {
            System.out.println("Exception" + e);
            return response.create("FAILURE",500);
        }
    }
}
