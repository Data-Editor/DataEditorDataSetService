package com.niek125.datasetservice.handler;

import com.jayway.jsonpath.DocumentContext;
import com.niek125.datasetservice.events.RowEditedEvent;
import com.niek125.datasetservice.file.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RowEditedHandlerMethod extends HandlerMethod<RowEditedEvent> {
    private final Logger logger = LoggerFactory.getLogger(RowEditedHandlerMethod.class);
    private final FileHandler fileHandler;

    @Autowired
    public RowEditedHandlerMethod(FileHandler fileHandler) {
        super(RowEditedEvent.class);
        this.fileHandler = fileHandler;
    }

    @Override
    public void handle(RowEditedEvent event) {
        try {
            logger.info("getting json");
            final DocumentContext json = fileHandler.getJSON(event.getProjectid());
            logger.info("editing row");
            json.set("$.items[" + event.getRownumber() + "]", event.getRow());
            logger.info("writing file");
            fileHandler.overwriteFile(json, event.getProjectid());
        } catch (IOException e) {
            logger.error("could not find file: {}", e.getMessage());
        }
    }
}
