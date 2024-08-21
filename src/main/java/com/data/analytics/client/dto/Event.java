package com.data.analytics.client.dto;

import com.data.analytics.client.dto.enums.PublishEventType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Event {
    private PublishEventType type;
    private String topic;
    private Object payload;
}
