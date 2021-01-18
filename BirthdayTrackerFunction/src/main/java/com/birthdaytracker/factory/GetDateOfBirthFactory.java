package com.birthdaytracker.factory;

import com.birthdaytracker.ddb.model.BirthdayTracker;
import com.birthdaytracker.model.GetDateOfBirthResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
public class GetDateOfBirthFactory {
    @NonNull private ParseFactory parseFactory;

    /**
     * retrieveDetails.
     */
    public GetDateOfBirthResult retrieveDetails(BirthdayTracker result) {
        GetDateOfBirthResult res = GetDateOfBirthResult.builder()
                .name(result.getName()).dateOfBirth(result.getDateOfBirth())
                .age(retrieveAge(result.getDateOfBirth())).build();
        return res;
    }

    /**
     * retrieveAge.
     */
    public int retrieveAge(String dateOfBirth) {
        LocalDate dateofbirth = LocalDate.of(parseFactory
                        .create(dateOfBirth, "year"),
                parseFactory.create(dateOfBirth, "month"),
                parseFactory.create(dateOfBirth, "date"));
        LocalDate currentdate = LocalDate.now();
        Period diff = Period.between(dateofbirth, currentdate);
        return diff.getYears();
    }
}
