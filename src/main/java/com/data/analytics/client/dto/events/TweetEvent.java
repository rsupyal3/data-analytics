package com.data.analytics.client.dto.events;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TweetEvent {

    private String tweetId;

    private long timestamp;

    private String hashTag;
}
