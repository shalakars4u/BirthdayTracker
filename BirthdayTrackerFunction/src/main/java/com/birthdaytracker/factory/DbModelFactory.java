package com.birthdaytracker.factory;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.birthdaytracker.model.InsertDateRequest;
import com.birthdaytracker.utils.Json;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DbModelFactory {
    @NonNull Json json;

    /**
     * Create.
     */
    public BirthdayTracker create(APIGatewayProxyRequestEvent event) {
        InsertDateRequest tracker = json.deserialize(InsertDateRequest.class, event.getBody());
        BirthdayTracker birthdayTracker = BirthdayTracker.builder()
                .name(tracker.getName())
                .dateOfBirth(tracker.getDateOfBirth())
                .month(getMonth(tracker.getDateOfBirth()))
                .date(getDates(tracker.getDateOfBirth()))
                .build();

        return birthdayTracker;
    }

    /**
     * getMonth.
     */
    public int getMonth(String dateOfBirth) {
        String[] res = dateOfBirth.split("/");
        return Integer.parseInt(res[0]);

    }

    /**
     * getDates.
     */
    public int getDates(String dateOfBirth) {
        String[] res = dateOfBirth.split("/");
        return Integer.parseInt(res[1]);

    }
}
