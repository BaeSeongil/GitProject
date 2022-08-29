package com.project.mainPage.util;


import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//use:jackson
public class JsonUtil {
    // json -> 객체 변환
    public static Object jsonToObj(Class objClass, String jsonStr) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, objClass);
    }

    // 객체 -> json 변환
    public static String objToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}