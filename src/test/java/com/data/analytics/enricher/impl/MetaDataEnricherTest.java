package com.data.analytics.enricher.impl;

import com.data.analytics.dto.EventPayload;
import com.data.analytics.dto.EventStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MetaDataEnricherTest {

    @InjectMocks
    private MetaDataEnricher metaDataEnricher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnrichWhenEventIdIsNull() {
        EventPayload payload = new EventPayload();
        payload.setEventId(null);
        payload.setTimestamp(123456789L);
        payload.setStatus(EventStatus.NEW.toString());

        EventPayload enrichedPayload = metaDataEnricher.enrich(payload);

        assertNotNull(enrichedPayload.getEventId());
    }

    @Test
    void testEnrichWhenEventIdIsEmpty() {
        EventPayload payload = new EventPayload();
        payload.setEventId("");
        payload.setTimestamp(123456789L);
        payload.setMetadata(new HashMap<>());
        payload.setStatus(EventStatus.NEW.toString());

        EventPayload enrichedPayload = metaDataEnricher.enrich(payload);

        assertNotNull(enrichedPayload.getEventId());
    }

    @Test
    void testEnrichWhenTimestampIsZero() {
        EventPayload payload = new EventPayload();
        payload.setEventId("eventId");
        payload.setTimestamp(0);
        payload.setMetadata(new HashMap<>());
        payload.setStatus(EventStatus.NEW.toString());

        EventPayload enrichedPayload = metaDataEnricher.enrich(payload);

        assertTrue(enrichedPayload.getTimestamp() != 0);
    }

    @Test
    void testEnrichWhenMetadataIsNull() {
        EventPayload payload = new EventPayload();
        payload.setEventId("eventId");
        payload.setTimestamp(123456789L);
        payload.setMetadata(null);
        payload.setStatus(EventStatus.NEW.toString());

        EventPayload enrichedPayload = metaDataEnricher.enrich(payload);

        assertTrue(enrichedPayload.getMetadata().containsKey("enrichedBy"));
        assertTrue(enrichedPayload.getMetadata().containsKey("enrichmentTimestamp"));
    }

    @Test
    void testEnrichSetsStatusToEnriched() {
        EventPayload payload = new EventPayload();
        payload.setEventId("eventId");
        payload.setTimestamp(123456789L);
        payload.setMetadata(new HashMap<>());
        payload.setStatus(EventStatus.NEW.toString());

        EventPayload enrichedPayload = metaDataEnricher.enrich(payload);

        assertEquals(EventStatus.ENRICHED.toString(), enrichedPayload.getStatus());
    }
}