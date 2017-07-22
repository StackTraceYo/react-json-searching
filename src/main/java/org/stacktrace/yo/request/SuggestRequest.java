package org.stacktrace.yo.request;

/**
 * Created by Ahmad on 7/21/2017.
 */
public class SuggestRequest {

    private String jsonFile;
    private String input;

    public SuggestRequest() {

    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }
}
