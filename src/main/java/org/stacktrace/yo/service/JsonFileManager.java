package org.stacktrace.yo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.stacktrace.yo.internal.LoadedJsonObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by afarag on 7/19/2017.
 */

@Component
public class JsonFileManager {

    private final ObjectMapper objectMapper;
    private final ConcurrentHashMap<String, LoadedJsonObject> loadedJsonFiles;
    private List<String> files;
    Logger LOGGER = LoggerFactory.getLogger("JsonFileManager");

    @Autowired
    public JsonFileManager(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        loadedJsonFiles = new ConcurrentHashMap<>();
        //            JsonNode defaultMap = objectMapper.readTree(getClass().getClassLoader().getResource("translation.json"));
        try {
            files = Files.walk(Paths.get(getClass().getClassLoader().getResource("json").toURI()))
                    .filter(Files::isRegularFile)
                    .filter(file -> StringUtils.getFilenameExtension(file.getFileName().toString()).equals("json"))
                    .map(jsonFile -> {
                        try {
                            String filename = jsonFile.getFileName().toString().split("\\.json")[0];
                            LOGGER.info("Found: {}", filename);
                            JsonNode node = objectMapper.readTree(jsonFile.toFile());
                            loadedJsonFiles.put(filename, new LoadedJsonObject().setJsonDocument(node));
                            return filename;
                        } catch (IOException e) {
                            return "";
                        }
                    })
                    .filter(name -> !name.isEmpty())
                    .collect(Collectors.toList());
        } catch (URISyntaxException e) {
            LOGGER.warn("Failed to Load Object");
        } catch (IOException e) {
            LOGGER.warn("Failed to Load Object");
        }
    }


    public LoadedJsonObject getJson(String jsonName) {
        if (StringUtils.isEmpty(jsonName)) {
            return getDefaultJson();
        } else {
            return loadedJsonFiles.get(jsonName);

        }
    }

    public Map getJsonAsHashMap(JsonNode node) {
        return objectMapper.convertValue(node, Map.class);
    }

    private LoadedJsonObject getDefaultJson() {
        return loadedJsonFiles.get("translation");
    }

    public List<String> getLoadedFiles(){
        return this.files;
    }
}
