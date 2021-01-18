package com.birthdaytracker.factory;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.birthdaytracker.model.InsertDateRequest;
import com.birthdaytracker.utils.Json;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DbModelFactory {
    @NonNull private Json json;

    @NonNull private ParseFactory parseFactory;

    /**
     * Create.
     */
    public BirthdayTracker create(APIGatewayProxyRequestEvent event) {
        InsertDateRequest tracker = json.deserialize(InsertDateRequest.class, event.getBody());
        BirthdayTracker birthdayTracker = BirthdayTracker.builder()
                .name(tracker.getName())
                .dateOfBirth(tracker.getDateOfBirth())
                .month(parseFactory.create(tracker.getDateOfBirth(), "month"))
                .date(parseFactory.create(tracker.getDateOfBirth(), "date"))
                .build();

        return birthdayTracker;
    }

    /**
     * Create.
     */
    public BirthdayTracker create(int month, int date) {
        BirthdayTracker birthdayTracker = BirthdayTracker.builder()
                .month(month)
                .date(date)
                .build();
        return birthdayTracker;
    }
}
