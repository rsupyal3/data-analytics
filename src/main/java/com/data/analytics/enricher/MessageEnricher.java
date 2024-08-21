package com.data.analytics.enricher;

import com.data.analytics.dto.EventPayload;

public interface MessageEnricher {
    EventPayload enrich(EventPayload payload);
}
