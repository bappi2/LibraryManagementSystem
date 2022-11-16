package com.mmefta.lms.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectMapperUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T deserialize(String data, Class<T> clazz) throws IOException {
        try {
            return OBJECT_MAPPER.readValue(data, clazz);
        } catch (IOException var3) {
            //logger.error("Failed to deserialize object;" + ExceptionUtilsEx.getExceptionInformation(var3));
            throw var3;
        }
    }
}
