package com.niek125.datasetservice.handler;

import com.niek125.datasetservice.events.DataEditorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UnrecognizedEventHandlerMethod extends HandlerMethod {
    private final Logger logger = LoggerFactory.getLogger(UnrecognizedEventHandlerMethod.class);

    protected UnrecognizedEventHandlerMethod() {
        super(DataEditorEvent.class);
    }


    @Override
    public void handle(DataEditorEvent event) {
        logger.info("could not find event: {}", event.getClass().getSimpleName());
    }
}
