package com.birthdaytracker.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor

public class Json {

    @NonNull
    private final ObjectMapper om;

    /**
     * Deserialize.
     */
    public <T> T deserialize(Class<T> type, final String modelJson) {

        try {
            return om.readValue(modelJson, type);
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }

    /**
     * deserialize.
     */
    public <T> T deserialize(final TypeReference<T> type, final String jsonString) {
        try {
            return om.readValue(jsonString, type);
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }

    /**
     * deserialize.
     */
    public <T> T deserialize(Class<T> type, final JsonNode jsonNode) {
        return deserialize(type, jsonNode.toString());
    }

    public <T> T deserialize(final TypeReference<T> type, final JsonNode jsonNode) {
        return deserialize(type, jsonNode.toString());
    }

    /**
     * serialize.
     */
    public <T> String serialize(final T model) {
        try {
            return om.writerFor(new TypeReference<T>() {
            }).writeValueAsString(model);
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }

    /**
     * asNode.
     */
    public JsonNode asNode(final Object modelObject) {
        try {
            return om.valueToTree(modelObject);
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }

    public List<JsonNode> asNodeList(final Object modelObject) {
        return deserialize(new TypeReference<List<JsonNode>>() {
        }, modelObject.toString());
    }
}