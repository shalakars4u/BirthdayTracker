package com.birthdaytracker.factory;

public class ParseFactory {

    /**
     * Create.
     */
    public int create(String dateOfBirth, String type) {
        String[] res = dateOfBirth.split("/");
        if (type.equals("month")) {
            return Integer.parseInt(res[0]);
        } else if (type.equals("date")) {
            return Integer.parseInt(res[1]);
        } else {
            return Integer.parseInt(res[2]);
        }

    }
}
