package com.data.analytics.client.dto.events;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContentPreferenceEvent {

    private String userId;

    private long timestamp;

    private int rating;

    private String genre;
}
