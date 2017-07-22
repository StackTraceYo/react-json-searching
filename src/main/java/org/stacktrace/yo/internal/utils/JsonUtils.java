package org.stacktrace.yo.internal.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stacktrace.yo.internal.TraverseObject;

import java.util.*;


public class JsonUtils {

    private static Logger LOGGER = LoggerFactory.getLogger("JsonUtils");


    public static TraverseObject createAutoCompleteTraversalObject(String input) {
        TraverseObject traverseObject = new TraverseObject();
        if (input.contains(".")) {
            if (input.endsWith(".")) {
                traverseObject.setTraverseList(Arrays.asList(input.split("\\.")));
            } else {
                List<String> tokens = Arrays.asList(input.split("\\."));
                String lastToken = tokens.get(tokens.size() - 1); // save last token before dropping
                traverseObject.setSearchWord(lastToken);
                traverseObject.setTraverseList(tokens.subList(0, tokens.size() - 1));
            }
        } else {
            traverseObject.setSearchWord(input);
            traverseObject.setPath(input);
        }
        return traverseObject;
    }

    public static TraverseObject createSearchTraversalObject(String input) {
        TraverseObject traverseObject =
                new TraverseObject()
                        .setTraverseList(
                                Arrays.asList(input.split("\\."))
                        );
        return traverseObject;
    }

    public static JsonNode traverse(JsonNode full, TraverseObject traverseObject) {
        int index = 0;
        JsonNode currentNode = full;
        while (index < traverseObject.getTraverseList().size()) {
            if (currentNode.isTextual()) {
                return currentNode;
            } else {
                //has keys
                currentNode = currentNode.get(traverseObject.getTraverseList().get(index));
            }
            index++;
        }
        return currentNode;
    }

    public static JsonNode hardTraverse(JsonNode full, TraverseObject traverseObject) {
        int index = 0;
        JsonNode currentNode = full;
        while (index < traverseObject.getTraverseList().size()) {
            if (currentNode.isTextual()) {
                if (index < traverseObject.getTraverseList().size() - 1) { // string found when should be able to go further
                    return null;
                } else {
                    return currentNode;
                }
            } else {
                //has keys
                currentNode = currentNode.get(traverseObject.getTraverseList().get(index));
            }
            index++;
        }
        return currentNode;
    }

    public static Map<String, String> traverseAndGetAllKeysWithValue(JsonNode node, String previousKey) {
        JsonNode currentNode = node;
        Map<String, String> response = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> iterator = currentNode.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator.next();
            String field = entry.getKey();
            JsonNode fieldValue = entry.getValue();
            String currentPath = previousKey.isEmpty() ? field : previousKey + "." + field;
            if (fieldValue.isTextual()) {
                LOGGER.info("Field {} : with value: {}", currentPath, fieldValue);
                response.put(currentPath, fieldValue.textValue());
            } else {
                response.putAll(traverseAndGetAllKeysWithValue(fieldValue, currentPath));
            }
            previousKey = iterator.hasNext() ? previousKey : "";
        }
        return response;
    }


}
