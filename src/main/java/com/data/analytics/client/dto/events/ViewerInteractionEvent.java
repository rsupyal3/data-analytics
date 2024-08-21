package com.data.analytics.client.dto.events;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ViewerInteractionEvent {

    private String userId;

    private String movie;

    private long timestamp;

    private String action;

    private String device;
}
