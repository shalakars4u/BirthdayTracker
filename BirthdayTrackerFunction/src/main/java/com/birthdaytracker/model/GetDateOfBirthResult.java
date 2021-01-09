package com.birthdaytracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDateOfBirthResult {
    private String name;
    private String dateOfBirth;
    private int age;

}
