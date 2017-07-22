package org.stacktrace.yo.internal;

import com.fasterxml.jackson.databind.JsonNode;
import org.stacktrace.yo.internal.utils.JsonUtils;

import java.util.Map;

/**
 * Created by Ahmad on 7/22/2017.
 */
public class LoadedJsonObject {

    private JsonNode jsonDocument;
    private Map<String, String> keyValues;

    public LoadedJsonObject() {
    }

    public JsonNode getJsonDocument() {
        return jsonDocument;
    }

    public LoadedJsonObject setJsonDocument(JsonNode jsonDocument) {
        this.jsonDocument = jsonDocument;
        this.keyValues = JsonUtils.traverseAndGetAllKeysWithValue(jsonDocument, "");
        return this;
    }

    public Map<String, String> getKeyValues() {
        return keyValues;
    }

    public LoadedJsonObject setKeyValues(Map<String, String> keyValues) {
        this.keyValues = keyValues;
        return this;
    }
}
