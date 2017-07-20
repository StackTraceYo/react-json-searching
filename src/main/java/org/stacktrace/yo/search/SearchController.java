package org.stacktrace.yo.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stacktrace.yo.search.service.JsonSearchService;

/**
 * @author Ahmad Farag
 */
@RestController
public class SearchController {

    final JsonSearchService searchService;

    @Autowired
    public SearchController(JsonSearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/api/search")
    public SearchResponse search() {
        return searchService.searchJson();
    }

    @RequestMapping(value = "/api/autocomplete")
    public SearchResponse autoComplete() {
        return searchService.getCurrentKeys();
    }

    @RequestMapping(value = "/api/autocomplete/{input}")
    public SearchResponse autoComplete(@PathVariable String input) {
        return searchService.getCurrentKeys(input);
    }

}