package com.niek125.datasetservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.niek125.datasetservice.handlers.FileHandler;
import com.niek125.datasetservice.models.Action;
import com.niek125.datasetservice.models.KafkaHeader;
import com.niek125.datasetservice.models.KafkaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDataConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaDataConsumer.class);
    private final ObjectMapper objectMapper;
    private final FileHandler fileHandler;

    @Autowired
    public KafkaDataConsumer(ObjectMapper objectMapper, FileHandler fileHandler) {
        this.objectMapper = objectMapper;
        this.fileHandler = fileHandler;
    }

    @KafkaListener(topics = "message", groupId = "message-consumer")
    public void consume(String message) {
        logger.info("received message: " + message);
        logger.info("parsing data");
        final String[] pay = message.split("\n");
        final DocumentContext doc = JsonPath.parse(pay[0]);
        final KafkaMessage kafkaMessage = new KafkaMessage(new KafkaHeader(Action.valueOf(doc.read("$.action")), doc.read("$.payload")), pay[1]);
        switch (kafkaMessage.getKafkaHeader().getAction()) {
            case CREATE:
                logger.info("creating row");
                fileHandler.createRow();
                break;
            case UPDATE:
                logger.info("editing row");
                fileHandler.editRow();
                break;
            case DELETE:
                logger.info("deleting row");
                fileHandler.deleteRow();
                break;
        }
        logger.info("successfully processed message");
    }
}
