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
            return searchService.getSuggestions(suggestRequest);
        }
    }

    @RequestMapping(value = "/api/search")
    public SearchResponse search(@RequestBody SearchRequest searchRequest) {
        if (searchRequest.getReverse()) {
            LOGGER.info("Reverse...");
            return searchService.getJsonKeys(searchRequest);
        } else {
            return searchService.getJsonValue(searchRequest);
        }
    }

    @RequestMapping(value = "/api/files", method = RequestMethod.GET)
    public AvailableResponse search() {
        return searchService.getLoadedFiles();

    }

}