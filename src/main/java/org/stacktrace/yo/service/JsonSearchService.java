package org.stacktrace.yo.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.stacktrace.yo.internal.LoadedJsonObject;
import org.stacktrace.yo.internal.TraverseObject;
import org.stacktrace.yo.internal.utils.JsonUtils;
import org.stacktrace.yo.request.SearchRequest;
import org.stacktrace.yo.request.SuggestRequest;
import org.stacktrace.yo.response.AvailableResponse;
import org.stacktrace.yo.response.SearchResponse;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by afarag on 7/19/2017.
 */

@Component
public class JsonSearchService {

    Logger LOGGER = LoggerFactory.getLogger("JsonSearchService");

    private final JsonFileManager manager;

    @Autowired
    public JsonSearchService(JsonFileManager manager) {
        this.manager = manager;
    }

    public SearchResponse getSuggestions(SuggestRequest request) {
        //default list of suggestions is empty
        LOGGER.info("Suggesting.. {}", request.getInput());
        SearchResponse response = new SearchResponse().setKeys(new ArrayList<>());
        if (!StringUtils.isEmpty(request.getInput())) { //if user has input
            TraverseObject traverseObject = JsonUtils.createAutoCompleteTraversalObject(request.getInput());
            response.setJsonPath(traverseObject.getPath());
            if (traverseObject.needsTraversal()) {
                LOGGER.info("Split Tokens Found: {} ", traverseObject.getTraverseList().size());
                JsonNode searched = JsonUtils.traverse(manager.getJson(request.getJsonFile()).getJsonDocument(), traverseObject);
                if (searched != null && searched.isObject()) {
                    LOGGER.info("Object Found");
                    Map<String, Object> objectMap = manager.getJsonAsHashMap(searched);
                    if (traverseObject.hasSearchWord()) {
                        LOGGER.info("Filtering Leftover Search");
                        response.setKeys( //find keys that contain the text is the search bar
                                objectMap.keySet()
                                        .stream()
                                        .filter(token -> token.contains(traverseObject.getSearchWord()))
                                        .map(filtered -> traverseObject.getPath() + "." + filtered)
                                        .collect(Collectors.toList())
                        );
                    } else {
                        response.setKeys(new ArrayList<>(objectMap.keySet()
                                .stream()
                                .map(token -> traverseObject.getPath() + "." + token)
                                .collect(Collectors.toList())));
                    }
                }
            } else { //just search word
                Map<String, Object> topSet = manager.getJsonAsHashMap(
                        manager.getJson(request.getJsonFile()).getJsonDocument()
                );
                if (traverseObject.hasSearchWord()) {
                    response.setKeys( //find keys that contain the text is the search bar
                            topSet.keySet()
                                    .stream()
                                    .filter(token -> token.contains(request.getInput()))
                                    .collect(Collectors.toList())
                    );
                } else {
                    response.setKeys(new ArrayList<>(topSet.keySet()));
                }
            }
        }
        return response;
    }

    public SearchResponse getJsonValue(SearchRequest request) {
        //default list of suggestions is empty
        LOGGER.info("Searching.. {}", request.getInput());
        SearchResponse response = new SearchResponse();
        if (!StringUtils.isEmpty(request.getInput())) { //if user has input
            TraverseObject traverseObject = JsonUtils.createSearchTraversalObject(request.getInput());
            response.setJsonPath(traverseObject.getPath());
            if (traverseObject.needsTraversal()) {
                LOGGER.info("Split Tokens Found: {} ", traverseObject.getTraverseList().size());
                JsonNode searched = JsonUtils.hardTraverse(manager.getJson(request.getJsonFile()).getJsonDocument(), traverseObject);
                if (searched != null) {
                    if (searched.isObject()) {
                        Map<String, Object> objectMap = manager.getJsonAsHashMap(searched);
                        response.setJsonValue(objectMap);
                    } else { //must be string
                        response.setJsonValue(searched.textValue());
                    }
                } else {
                    response.setJsonValue("Key Is not Available");
                }
            }
        } else {
            response.setJsonValue("No Input Provided");
        }
        return response;
    }

    public SearchResponse getJsonKeys(SearchRequest request) {
        LOGGER.info("Searching.. {}", request.getInput());
        SearchResponse response = new SearchResponse();
        response.setJsonPath(request.getInput());
        if (!StringUtils.isEmpty(request.getInput())) { //if user has input
            LoadedJsonObject jsonFile = manager.getJson(request.getJsonFile());
            response.setKeys(jsonFile.getKeyValues()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().toLowerCase().contains(request.getInput().toLowerCase()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList()));
        }
        if(response.getKeys().isEmpty()){
            response.setMessage("No Keys Match The Search Value");
        }
        return response;
    }

    public AvailableResponse getLoadedFiles() {
        return new AvailableResponse().setFiles(manager.getLoadedFiles());
    }


}
