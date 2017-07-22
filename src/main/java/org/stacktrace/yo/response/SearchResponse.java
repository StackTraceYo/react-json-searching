package org.stacktrace.yo.response;

import java.util.List;

/**
 * Created by afarag on 7/19/2017.
 */
public class SearchResponse {

    private List<String> keys;
    private Object jsonValue;
    private String jsonPath;
    private String message;

    public SearchResponse() {
    }

    public List<String> getKeys() {
        return keys;
    }

    public SearchResponse setKeys(List<String> keys) {
        this.keys = keys;
        return this;
    }

    public SearchResponse addKey(String key) {
        keys.add(key);
        return this;
    }

    public Object getJsonValue() {
        return jsonValue;
    }

    public SearchResponse setJsonValue(Object jsonValue) {
        this.jsonValue = jsonValue;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SearchResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public SearchResponse setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
        return this;
    }
}
