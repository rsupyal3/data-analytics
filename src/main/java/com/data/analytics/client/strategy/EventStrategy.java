package com.data.analytics.client.strategy;

import com.data.analytics.client.dto.Event;

public interface EventStrategy {
    void processEvent(Event event);
}
