package com.birthdaytracker.ddb.client;

public interface IDDBClient {

    void deleteItem(Object item);
    void saveItem(Object item);
    <T> T loadItem(String hashKey,Class<T> type);
}
