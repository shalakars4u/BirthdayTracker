package com.birthdaytracker.utils;

import com.birthdaytracker.model.InsertDateRequest;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class JsonTest {

    public static final TypeReference<InsertDateRequest> RETURN_TYPE = new TypeReference<InsertDateRequest>() {};

    private class UsableException extends JsonProcessingException {
        protected UsableException(String msg, JsonLocation loc, Throwable rootCause) {
            super(msg, loc, rootCause);
        }
    }

    private static final  String NAME = "NAME";
    private String DATEOFBIRTH = "DATEOFBIRTH";

    @Mock
    private ObjectMapper omMock;
    private Json sut;

    @BeforeEach
    public void setup() {

        initMocks(this);
        sut = new Json(omMock);
    }


    @Test
    public void test_constructor_AnyArgsNull_ThrowsNullPointerException() {

        assertThrows(NullPointerException.class, () -> new Json(null));
    }

    @Test
    public void test_DeserializeClass_NoThrow_ReturnModel() throws IOException {
        InsertDateRequest output = new InsertDateRequest(NAME, DATEOFBIRTH);
        when(omMock.readValue(eq(""), eq(RETURN_TYPE))).thenReturn(output);
        InsertDateRequest result = sut.deserialize(RETURN_TYPE, "");
        assertEquals(output, result);

    }

    @Test
    public void test_DeserializeType_NoThrow_ReturnModel() throws IOException {
        InsertDateRequest output = new InsertDateRequest(NAME, DATEOFBIRTH);
        when(omMock.readValue(eq(""), eq(InsertDateRequest.class))).thenReturn(output);
        InsertDateRequest result = sut.deserialize(InsertDateRequest.class, "");
        assertEquals(output, result);
    }

    @Test
    public void test_DeserializeClass_ThrowsAny_ReturnModel() throws IOException {
        when(omMock.readValue(eq(""), eq(InsertDateRequest.class))).thenThrow(new NullPointerException(TestConstants.EXPECTED_EXCEPTION_MESSAGE));
        assertNull(sut.deserialize(InsertDateRequest.class, ""));
    }

    @Test
    public void test_DeserializeType_ThrowsAny_ReturnNull() throws IOException {
        when(omMock.readValue(eq(""), eq(InsertDateRequest.class))).thenThrow(new NullPointerException(TestConstants.EXPECTED_EXCEPTION_MESSAGE));
        assertNull(sut.deserialize(RETURN_TYPE, ""));
    }

    @Test
    public void test_Serialize_ThrowsAny_ReturnNull() throws IOException {
        InsertDateRequest input = new InsertDateRequest(NAME, DATEOFBIRTH);
        when(omMock.writeValueAsString(input)).thenThrow(new UsableException("", new JsonLocation(new Object(), 0, 0, 0), new Throwable()));
        assertNull( sut.serialize(input));
    }

    @Test
    public void test_Serialize() throws IOException {
       Json json = new Json(new ForgivingObjectMapperFactory().create());
        InsertDateRequest request =  InsertDateRequest.builder().name("shalaka").dateOfBirth("07/11/1992").build();
        String value="{\"name\":\"shalaka\",\"dateOfBirth\":\"07/11/1992\"}";
        assertEquals(value, json.serialize(request));
    }
}
