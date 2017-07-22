package org.stacktrace.yo.request;

/**
 * Created by Ahmad on 7/21/2017.
 */
public class SearchRequest {

    private String jsonFile;
    private String input;
    private Boolean reverse;

    public SearchRequest() {

    }

    public Boolean getReverse() {
        return reverse;
    }

    public SearchRequest setReverse(Boolean reverse) {
        this.reverse = reverse;
        return this;
    }

    public String getInput() {
        return input;
    }

    public SearchRequest setInput(String input) {
        this.input = input;
        return this;
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public SearchRequest setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
        return this;
    }
}
