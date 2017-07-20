package org.stacktrace.yo.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afarag on 7/19/2017.
 */
public class SearchResponse {

    private List<String> keys;

    public SearchResponse() {
        this.keys = new ArrayList<>();
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public SearchResponse addKey(String key) {
        keys.add(key);
        return this;
    }
}
