package com.niek125.datasetservice.kafka;

import com.niek125.datasetservice.handler.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDataConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaDataConsumer.class);
    private final EventHandler eventHandler;

    @Autowired
    public KafkaDataConsumer(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = {"data", "project"}, groupId = "data-consumer")
    public void consume(String message) {
        logger.info("received message: {}", message);
        eventHandler.processMessage(message);
        logger.info("successfully processed message");
    }
}
