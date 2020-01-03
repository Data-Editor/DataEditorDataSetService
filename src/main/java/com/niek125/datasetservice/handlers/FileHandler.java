package com.niek125.datasetservice.handlers;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileHandler {
    @Value("${com.niek125.filedump}")
    private String filedir;

    private DocumentContext getJSON(String projectid) throws IOException {
        return JsonPath.parse(new String(Files.readAllBytes(Paths.get(filedir + projectid + ".json"))));
    }

    private void overwriteFile(DocumentContext json, String projectid) throws IOException {
        final FileWriter fileWriter = new FileWriter(filedir + projectid + ".json", false);
        fileWriter.write(json.jsonString());
        fileWriter.close();
    }

    public void createRow(String projectid, HashMap item) throws IOException {
        final DocumentContext json = getJSON(projectid);
        json.add("$.items", item);
        overwriteFile(json, projectid);
    }

    public void editRow(String projectid, HashMap item, int row) throws IOException {
        final DocumentContext json = getJSON(projectid);
        json.set("$.items[" + row + "]",item);
        overwriteFile(json, projectid);
    }

    public void deleteRow(String projectid, int row) throws IOException {
        final DocumentContext json = getJSON(projectid);
        json.delete("$.items[" + row + "]");
        overwriteFile(json, projectid);
    }
}
