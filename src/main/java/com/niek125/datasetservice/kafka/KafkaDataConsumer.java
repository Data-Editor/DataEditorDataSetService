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

import java.io.IOException;

@Service
public class KafkaDataConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaDataConsumer.class);
    private final FileHandler fileHandler;

    @Autowired
    public KafkaDataConsumer(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @KafkaListener(topics = "data", groupId = "data-consumer")
    public void consume(String message) throws IOException {
        logger.info("received message: " + message);
        logger.info("parsing data");
        final String[] pay = message.split("\n");
        final DocumentContext doc = JsonPath.parse(pay[0]);
        final KafkaMessage kafkaMessage = new KafkaMessage(new KafkaHeader(Action.valueOf(doc.read("$.action")), doc.read("$.payload")), JsonPath.parse(pay[1]));
        switch (kafkaMessage.getKafkaHeader().getAction()) {
            case CREATE:
                logger.info("creating row");
                fileHandler.createRow(kafkaMessage.getPayload().read("$.projectid", String.class), kafkaMessage.getPayload().read("$.row"));
                break;
            case UPDATE:
                logger.info("editing row");
                fileHandler.editRow(kafkaMessage.getPayload().read("$.projectid", String.class), kafkaMessage.getPayload().read("$.row"), kafkaMessage.getPayload().read("$.rownumber"));
                break;
            case DELETE:
                logger.info("deleting row");
                fileHandler.deleteRow(kafkaMessage.getPayload().read("$.projectid", String.class), kafkaMessage.getPayload().read("$.rownumber"));
                break;
        }
        logger.info("successfully processed message");
    }
}
