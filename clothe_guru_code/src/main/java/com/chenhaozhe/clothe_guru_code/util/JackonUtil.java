package com.chenhaozhe.clothe_guru_code.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class JackonUtil {
     private static ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode getNode(String json){
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String ObjectToJSON(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T JsonToObject(String json,Class<T> clazz){
        try {
            return objectMapper.readValue(json,clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

   public static String ListToJson(List list){
       try {
           return objectMapper.writeValueAsString(list);
       } catch (JsonProcessingException e) {
           throw new RuntimeException(e);
       }
   }

    public static String MapToJson(Map map){
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
