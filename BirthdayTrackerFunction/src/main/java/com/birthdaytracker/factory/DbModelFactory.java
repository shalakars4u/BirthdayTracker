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

    public BirthdayTracker create(APIGatewayProxyRequestEvent event) {
        InsertDateRequest tracker = json.deserialize(InsertDateRequest.class, event.getBody());
        BirthdayTracker birthdayTracker = BirthdayTracker.builder()
                .name(tracker.getName())
                .dateOfBirth(tracker.getDateOfBirth())
                .month(mothod.get(tracker.getDateOfBirth()))
                .date(othermethod.get(tracker.get))
                .build();

        return birthdayTracker;
    }
}
