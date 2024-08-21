package com.data.analytics.client.dto.enums;

public enum PublishEventType {
    VIEWER("view-events"),
    CONTENT("content-events"),
    APPLICATION("application-events"),

    TWEET_HASHTAG("tweet-hashtag");

    private final String topic;

    PublishEventType(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
