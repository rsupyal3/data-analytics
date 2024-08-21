package com.data.analytics.producer;

import com.data.analytics.dto.EventPayload;
import com.data.analytics.enricher.MessageEnricher;
import com.data.analytics.enricher.impl.MetaDataEnricher;
import com.data.analytics.exception.IngestionException;
import com.data.analytics.exception.constants.IngestionErrorCodes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final List<MessageEnricher> enrichers;
    private final MetaDataEnricher metaDataEnricher;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaProducerService(final KafkaTemplate<String, String> kafkaTemplate,
                                final MetaDataEnricher metaDataEnricher,
                                final ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.enrichers = new ArrayList<>();
        this.metaDataEnricher = metaDataEnricher;
        this.objectMapper = objectMapper;
        init();
    }

    private void init() {
        this.enrichers.add(metaDataEnricher);
    }

    public void send(EventPayload payload) {
        try {
            for (MessageEnricher enricher : enrichers) {
                payload = enricher.enrich(payload);
            }
            String message = convertPayloadToString(payload);
            kafkaTemplate.send(payload.getTopic(), message);
        } catch (Exception e) {
            log.error("[KafkaProducerService] : Error occurred while publishing the event", e);
            throw IngestionException.of(IngestionErrorCodes.INGESTION_ERROR);
        }
    }

    private String convertPayloadToString(EventPayload payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            log.error("[KafkaProducerService] : Error occurred while payload conversion to string", e);
            throw IngestionException.propagate(IngestionErrorCodes.MESSAGE_CONVERSION_ERROR, "JSON processing exception", e);
        }
    }
}
