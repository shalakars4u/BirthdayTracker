package com.birthdaytracker.functions;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.birthdaytracker.ddb.client.BirthdayTrackerMappingDdbClient;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.birthdaytracker.factory.DbModelFactory;
import com.birthdaytracker.factory.GetDateOfBirthFactory;
import com.birthdaytracker.factory.ResponseFactory;
import com.birthdaytracker.utils.Json;
import lombok.NonNull;
import org.apache.http.util.Args;


import java.util.List;
import java.util.Map;

public class GetNamesPerMonthLambda extends BaseInjectedLambda implements
        RequestHandler<APIGatewayProxyRequestEvent,
                APIGatewayProxyResponseEvent> {
    @NonNull
    BirthdayTrackerMappingDdbClient mapper;
    @NonNull
    ResponseFactory response;
    @NonNull
    DbModelFactory dbModelFactory;
    @NonNull Json json;

    /**
     * GetNamesPerMonthLambda.
     */
    public GetNamesPerMonthLambda() {
        this(getInjector().getInstance(BirthdayTrackerMappingDdbClient.class),
                getInjector().getInstance(ResponseFactory.class));
    }

    /**
     * GetNamesPerMonthLambda.
     */
    public GetNamesPerMonthLambda(final BirthdayTrackerMappingDdbClient mapper,
                                  final ResponseFactory response) {
        this.mapper = Args.notNull(mapper, "mapper");
        this.response = Args.notNull(response, "response");
    }

    /**
     * handleRequest.
     */
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {
        try {
            Map<String, String> pathParameters = input.getPathParameters();
            String month = pathParameters.get("month");
            String date = pathParameters.get("date");
            BirthdayTracker birthdayTracker = dbModelFactory
                    .create(Integer.valueOf(month), Integer.valueOf(date));
            List<BirthdayTracker> result =
                    mapper.retrieveNamesPerMonth(birthdayTracker);
            String output = json.serialize(result);
            return response.create(output, 200);
        } catch (Exception e) {
            System.out.println("Exception" + e);
            return response.create("FAILURE", 500);
        }

    }

}
