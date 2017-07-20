package org.stacktrace.yo.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by afarag on 7/19/2017.
 */

@Component
public class JsonFileManager {

    private final ObjectMapper objectMapper;
    private final ConcurrentHashMap<String, HashMap<String, ?>> loadedJsonFiles;

    @Autowired
    public JsonFileManager(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        loadedJsonFiles = new ConcurrentHashMap<>();
        try {
            HashMap defaultMap = objectMapper.readValue(getClass().getClassLoader().getResource("translation.json"), HashMap.class);
            System.out.println("Loaded Json");
            loadedJsonFiles.put("translation", defaultMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String test() {
        return (String) loadedJsonFiles.get("translation").get("title");
    }

    public HashMap<String, ?> getJsonMap() {
        return loadedJsonFiles.get("translation");
    }
}
