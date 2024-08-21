package com.data.analytics.client.strategy.impl;

import com.data.analytics.client.dto.Event;
import com.data.analytics.dto.EventPayload;
import com.data.analytics.producer.KafkaProducerService;
import com.data.analytics.client.strategy.EventStrategy;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApplicationHealthStrategy implements EventStrategy {
    private final KafkaProducerService producerService;

    @Autowired
    public ApplicationHealthStrategy(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    public void processEvent(Event event) {
        val eventPayload = EventPayload.builder()
                .topic(event.getTopic())
                .data(event.getPayload())
                .source("application-health")
                .build();
        producerService.send(eventPayload);
    }
}
