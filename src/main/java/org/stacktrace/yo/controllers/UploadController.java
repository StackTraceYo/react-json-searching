package org.stacktrace.yo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.stacktrace.yo.response.UploadResponse;
import org.stacktrace.yo.service.JsonFileManager;

/**
 * @author Ahmad Farag
 */
@RestController
public class UploadController {

    private final JsonFileManager manager;

    @Autowired
    public UploadController(JsonFileManager manager) {
        this.manager = manager;
    }


    @RequestMapping(value = "/api/upload")
    public UploadResponse upload(@RequestParam("filesToUpload") MultipartFile[] file) {
        return new UploadResponse()
                .setMessage("Success")
                .setSuccess(true);
    }

}