package com.niek125.datasetservice.handler;

import com.jayway.jsonpath.DocumentContext;
import com.niek125.datasetservice.events.RowCreatedEvent;
import com.niek125.datasetservice.file.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RowCreatedHandlerMethod extends HandlerMethod<RowCreatedEvent> {
    private final Logger logger = LoggerFactory.getLogger(RowCreatedHandlerMethod.class);
    private final FileHandler fileHandler;

    @Autowired
    public RowCreatedHandlerMethod(FileHandler fileHandler) {
        super(RowCreatedEvent.class);
        this.fileHandler = fileHandler;
    }

    @Override
    public void handle(RowCreatedEvent event) {
        try {
            logger.info("getting json");
            final DocumentContext json = fileHandler.getJSON(event.getProjectid());
            logger.info("adding row");
            json.add("$.items", event.getRow());
            logger.info("writing file");
            fileHandler.overwriteFile(json, event.getProjectid());
        } catch (IOException e) {
            logger.error("could not find file: {}", e.getMessage());
        }
    }
}
