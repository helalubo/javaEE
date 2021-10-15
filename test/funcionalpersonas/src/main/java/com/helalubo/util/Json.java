package com.helalubo.util;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Json {

    public Json() {
    }

    public static JSONArray LeerArrayJson(String path) {

        JSONArray data = null;
        String directoryName = System.getProperty("user.dir");
        try {
            JSONParser parser = new JSONParser();
            // Use JSONArray for simple JSON and JSONArray for array of JSON.
            data = (JSONArray) parser.parse(new FileReader(directoryName + path));// path to the JSON file.

            // System.out.println(data);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return data;
    }

    // public List<T> JsonToArrayListDeterminate(JSONArray data) throws
    // JsonMappingException, JsonProcessingException {
    // ObjectMapper mapper = new ObjectMapper();
    // List<T> list = new ArrayList<T>();
    // list = mapper.readValue(data.toJSONString(), new TypeReference<List<T>>() {
    // });

    // return list;

    // }

    // public void JsonToArrayListDeterminate(JSONArray data, List<T> list)
    // throws JsonMappingException, JsonProcessingException {
    // ObjectMapper mapper = new ObjectMapper();
    // List<T> listGenerica = new ArrayList<T>();
    // listGenerica = mapper.readValue(data.toJSONString(), new
    // TypeReference<List<T>>() {
    // });

    // for (final T object : listGenerica) {
    // list.add(object);
    // }

    // }

}
