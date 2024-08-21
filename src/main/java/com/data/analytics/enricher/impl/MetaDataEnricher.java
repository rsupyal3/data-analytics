package com.data.analytics.enricher.impl;

import com.data.analytics.dto.EventPayload;
import com.data.analytics.dto.EventStatus;
import com.data.analytics.enricher.MessageEnricher;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class MetaDataEnricher implements MessageEnricher {

    private static final String ENRICHED_BY = "enrichedBy";
    private static final String ENRICHMENT_TIMESTAMP = "enrichmentTimestamp";

    private static final String ENRICHER_NAME = "MetaDataEnricher";

    @Override
    public EventPayload enrich(EventPayload payload) {
        if (payload.getEventId() == null || payload.getEventId().isEmpty()) {
            payload.setEventId(generateUniqueEventId());
        }

        if (payload.getTimestamp() == 0) {
            payload.setTimestamp(System.currentTimeMillis());
        }

        Map<String, String> metadata = payload.getMetadata();
        if (metadata == null) {
            metadata = new HashMap<>();
        }
        metadata.put(ENRICHED_BY, ENRICHER_NAME);
        metadata.put(ENRICHMENT_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        payload.setMetadata(metadata);

        payload.setStatus(EventStatus.ENRICHED.toString());

        return payload;
    }

    private String generateUniqueEventId() {
        return UUID.randomUUID().toString();
    }
}
