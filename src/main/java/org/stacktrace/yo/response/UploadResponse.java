package org.stacktrace.yo.response;

/**
 * Created by afarag on 7/19/2017.
 */
public class UploadResponse {

    private Boolean success;
    private String message;

    public UploadResponse() {
    }

    public Boolean isSuccess() {
        return success;
    }

    public UploadResponse setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public UploadResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
