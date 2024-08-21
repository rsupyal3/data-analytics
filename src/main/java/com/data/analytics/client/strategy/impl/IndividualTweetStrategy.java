package com.data.analytics.client.strategy.impl;

import com.data.analytics.client.dto.Event;
import com.data.analytics.dto.EventPayload;
import com.data.analytics.producer.KafkaProducerService;
import com.data.analytics.client.strategy.EventStrategy;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IndividualTweetStrategy implements EventStrategy {
    private final KafkaProducerService producerService;

    @Autowired
    public IndividualTweetStrategy(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    public void processEvent(Event event) {
        val eventPayload = EventPayload.builder()
                .topic(event.getTopic())
                .data(event.getPayload())
                .source("individual-tweet")
                .build();
        producerService.send(eventPayload);
    }
}
