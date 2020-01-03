package com.niek125.datasetservice.models;

import com.jayway.jsonpath.DocumentContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KafkaMessage {
    private final KafkaHeader kafkaHeader;
    private final DocumentContext payload;
}
