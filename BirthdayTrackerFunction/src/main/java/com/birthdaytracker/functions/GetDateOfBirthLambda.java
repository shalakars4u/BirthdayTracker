package com.birthdaytracker.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.birthdaytracker.ddb.client.BirthdayTrackerMappingDdbClient;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.birthdaytracker.factory.DbModelFactory;
import com.birthdaytracker.factory.GetDateOfBirthFactory;
import com.birthdaytracker.factory.ResponseFactory;
import com.birthdaytracker.model.GetDateOfBirthResult;
import lombok.NonNull;
import org.apache.http.util.Args;


import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */

public class GetDateOfBirthLambda extends BaseInjectedLambda implements
        RequestHandler<APIGatewayProxyRequestEvent,
                APIGatewayProxyResponseEvent> {
    @NonNull
    BirthdayTrackerMappingDdbClient mapper;
    @NonNull
    ResponseFactory response;
    @NonNull
    GetDateOfBirthFactory dateFactory;

    /**
     * GetDateOfBirthLambda.
     */
    public GetDateOfBirthLambda() {
        this(getInjector().getInstance(BirthdayTrackerMappingDdbClient.class),
                getInjector().getInstance(ResponseFactory.class),
                getInjector().getInstance(GetDateOfBirthFactory.class));
    }

    /**
     * GetDateOfBirthLambda.
     */

    public GetDateOfBirthLambda(final BirthdayTrackerMappingDdbClient mapper,
                                final ResponseFactory response,
                                final GetDateOfBirthFactory dateFactory) {
        this.mapper = Args.notNull(mapper, "mapper");
        this.response = Args.notNull(response, "response");
        this.dateFactory = Args.notNull(dateFactory, "dateFactory");
    }

    /**
     * handleRequest.
     */
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {
        try {

            Map<String, String> pathParameters = input.getPathParameters();
            String name = pathParameters.get("name");
            BirthdayTracker result = mapper.getDateOfBirthRequestMapping(name);
            GetDateOfBirthResult getDateOfBirthresult = dateFactory
                    .retrieveDetails(result);
            String output = "The person " + getDateOfBirthresult.getName()
                    + " has "
                    + getDateOfBirthresult.getDateOfBirth()
                    + " date of birth and the age is " + getDateOfBirthresult.getAge();
            return response.create(output, 200);

        } catch (Exception e) {
            System.out.println("Exception" + e);
            return response.create("FAILURE", 500);
        }
    }
}
