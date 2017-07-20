package org.stacktrace.yo.search;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ahmad Farag
 */
@RestController
public class SearchController {

    @RequestMapping(value = "/api/search")
    public SearchResponse search() {
        return new SearchResponse()
                .addKey("hello")
                .addKey("what")
                .addKey("Test");
    }

}