package com.niek125.datasetservice.file;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileHandler {
    @Value("${com.niek125.filedump}")
    private String filedir;

    private Path getPath(String projectid){
        return Paths.get(filedir + projectid + ".json");
    }

    public DocumentContext getJSON(String projectid) throws IOException {
        return JsonPath.parse(new String(Files.readAllBytes(getPath(projectid))));
    }

    public void overwriteFile(DocumentContext json, String projectid) throws IOException {
        try (final FileWriter fileWriter = new FileWriter(filedir + projectid + ".json", false)) {
            fileWriter.write(json.jsonString());
        }
    }

    public void deleteFile(String projectid) throws IOException {
        Files.delete(getPath(projectid));
    }
}
