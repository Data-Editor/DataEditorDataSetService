package com.niek125.datasetservice.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.niek125.datasetservice.events.DataEditorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventParser {
    private final Logger logger = LoggerFactory.getLogger(EventParser.class);
    private final ObjectMapper objectMapper;
    @Value("${com.niek125.eventfolder}")
    private String eventfolder;

    @Autowired
    public EventParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public DataEditorEvent parseToEvent(String json){
        try {
            logger.info("parsing event");
            final String eventName = JsonPath.read(json, "$.class");
            return (DataEditorEvent) objectMapper.readValue((String) JsonPath.read(json, "$.event"),
                    Class.forName(eventfolder + eventName));
        } catch (JsonProcessingException e) {
            logger.info("event does not have the right structure");
        } catch (ClassNotFoundException e) {
            logger.debug("event not found");
        }
        return new DataEditorEvent();
    }
}
