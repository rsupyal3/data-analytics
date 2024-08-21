package com.data.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventPayload {
    private String topic;
    private String eventId;
    private long timestamp;
    private Object data;
    private Map<String, String> metadata;
    private String source;
    private String status;
}
