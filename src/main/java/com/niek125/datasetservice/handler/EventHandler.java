package com.niek125.datasetservice.handler;

import com.niek125.datasetservice.events.DataEditorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventHandler {
    private final List<HandlerMethod> handlerMethods;
    private final EventParser eventParser;
    private final UnrecognizedEventHandlerMethod unrecognizedEventHandlerMethod;

    @Autowired
    public EventHandler(List<HandlerMethod> handlerMethods, EventParser eventParser, UnrecognizedEventHandlerMethod unrecognizedEventHandlerMethod) {
        this.handlerMethods = handlerMethods;
        this.eventParser = eventParser;
        this.unrecognizedEventHandlerMethod = unrecognizedEventHandlerMethod;
    }

    public void processMessage(String message){
        final DataEditorEvent event = eventParser.parseToEvent(message);
        final HandlerMethod method = handlerMethods.stream()
                .filter(x -> x.getHandlingType() == event.getClass())
                .findFirst().orElse(unrecognizedEventHandlerMethod);
        method.handle(event);
    }
}
