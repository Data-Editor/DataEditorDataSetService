package com.niek125.datasetservice.handler;

import com.niek125.datasetservice.events.ProjectDeletedEvent;
import com.niek125.datasetservice.file.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProjectDeletedHandlerMethod extends HandlerMethod<ProjectDeletedEvent> {
    private final Logger logger = LoggerFactory.getLogger(ProjectDeletedHandlerMethod.class);
    private final FileHandler handler;

    @Autowired
    public ProjectDeletedHandlerMethod(FileHandler handler) {
        super(ProjectDeletedEvent.class);
        this.handler = handler;
    }

    @Override
    public void handle(ProjectDeletedEvent event) {
        try {
            logger.info("deleting file");
            handler.deleteFile(event.getProjectid());
        } catch (IOException e) {
            logger.error("could not find file: {}", e.getMessage());
        }
    }
}
