package org.stacktrace.yo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.stacktrace.yo.request.SearchRequest;
import org.stacktrace.yo.request.SuggestRequest;
import org.stacktrace.yo.response.AvailableResponse;
import org.stacktrace.yo.response.SearchResponse;
import org.stacktrace.yo.service.JsonSearchService;

import java.util.ArrayList;

/**
 * @author Ahmad Farag
 */
@RestController
public class SearchController {
    Logger LOGGER = LoggerFactory.getLogger("SearchController");

    final JsonSearchService searchService;

    @Autowired
    public SearchController(JsonSearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/api/suggest")
    public SearchResponse suggest(@RequestBody SuggestRequest suggestRequest) {
        {
            if (validateSuggest(suggestRequest)) {
                return searchService.getSuggestions(suggestRequest);

            } else {
                return new SearchResponse()
                        .setKeys(new ArrayList<>())
                        .setMessage("No Json File Selected");
            }
        }
    }

    @RequestMapping(value = "/api/search")
    public SearchResponse search(@RequestBody SearchRequest searchRequest) {
        if (validateSearch(searchRequest)) {
            if (searchRequest.getReverse()) {
                LOGGER.info("Reverse...");
                return searchService.getJsonKeys(searchRequest);
            } else {
                return searchService.getJsonValue(searchRequest);
            }
        } else {
            return new SearchResponse().setMessage("No Json File Selected");
        }
    }

    @RequestMapping(value = "/api/files", method = RequestMethod.GET)
    public AvailableResponse search() {
        return searchService.getLoadedFiles();

    }

    private boolean validateSearch(SearchRequest request) {
        return !request.getJsonFile().isEmpty();
    }

    private boolean validateSuggest(SuggestRequest request) {
        return !request.getJsonFile().isEmpty();
    }
}