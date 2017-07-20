package org.stacktrace.yo.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stacktrace.yo.json.JsonFileManager;
import org.stacktrace.yo.search.SearchResponse;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by afarag on 7/19/2017.
 */

@Component
public class JsonSearchService {

    private final JsonFileManager manager;

    @Autowired
    public JsonSearchService(JsonFileManager manager) {
        this.manager = manager;
    }

    public SearchResponse searchJson() {
        return new SearchResponse()
                .addKey("hello")
                .addKey("what")
                .addKey("Test")
                .addKey(manager.test());
    }

    public SearchResponse getCurrentKeys() {
        return new SearchResponse()
                .setKeys(
                        new ArrayList<>(manager.getJsonMap().keySet())
                );
    }

    public SearchResponse getCurrentKeys(String input) {
        return new SearchResponse()
                .setKeys(
                        manager.getJsonMap().keySet()
                                .stream()
                                .filter(key -> key.contains(input))
                                .collect(Collectors.toList()));
    }
}
