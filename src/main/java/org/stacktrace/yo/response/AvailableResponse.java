package org.stacktrace.yo.response;

import java.util.List;

/**
 * Created by afarag on 7/19/2017.
 */
public class AvailableResponse {

    private List<String> files;

    public AvailableResponse() {
    }

    public List<String> getFiles() {
        return files;
    }

    public AvailableResponse setFiles(List<String> files) {
        this.files = files;
        return this;
    }
}
