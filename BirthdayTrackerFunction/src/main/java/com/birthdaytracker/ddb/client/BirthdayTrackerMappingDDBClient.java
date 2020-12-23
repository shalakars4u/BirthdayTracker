package com.birthdaytracker.ddb.client;

import com.birthdaytracker.ddb.model.BirthdayTracker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BirthdayTrackerMappingDDBClient {

    @NonNull
    private IDDBClient ddbClient;
    public void saveNameDateRequestMapping(BirthdayTracker bd){
        ddbClient.saveItem(bd);
    }
}
