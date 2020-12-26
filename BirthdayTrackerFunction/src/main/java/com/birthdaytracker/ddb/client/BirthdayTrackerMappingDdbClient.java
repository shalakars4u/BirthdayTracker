package com.birthdaytracker.ddb.client;

import com.birthdaytracker.ddb.model.BirthdayTracker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BirthdayTrackerMappingDdbClient {

    @NonNull
    private IddbClient ddbClient;

    public void saveNameDateRequestMapping(BirthdayTracker bd) {
        ddbClient.saveItem(bd);
    }
}
