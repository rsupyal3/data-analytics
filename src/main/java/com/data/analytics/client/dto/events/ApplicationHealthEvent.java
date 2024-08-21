package com.data.analytics.client.dto.events;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApplicationHealthEvent {
    private String service;
    private long timestamp;
    private String metric;
    private int value;
}
