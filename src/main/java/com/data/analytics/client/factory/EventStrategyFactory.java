package com.data.analytics.client.factory;

import com.data.analytics.client.dto.enums.PublishEventType;
import com.data.analytics.client.strategy.EventStrategy;
import com.data.analytics.client.strategy.impl.ApplicationHealthStrategy;
import com.data.analytics.client.strategy.impl.ContentPreferenceStrategy;
import com.data.analytics.client.strategy.impl.IndividualTweetStrategy;
import com.data.analytics.client.strategy.impl.ViewerInteractionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class EventStrategyFactory {

    private final Map<PublishEventType, EventStrategy> strategies = new EnumMap<>(PublishEventType.class);

    @Autowired
    public EventStrategyFactory(final ViewerInteractionStrategy viewerInteractionStrategy,
                                final ContentPreferenceStrategy contentPreferenceStrategy,
                                final ApplicationHealthStrategy applicationHealthStrategy,
                                final IndividualTweetStrategy individualTweetStrategy) {
        strategies.put(PublishEventType.VIEWER, viewerInteractionStrategy);
        strategies.put(PublishEventType.CONTENT, contentPreferenceStrategy);
        strategies.put(PublishEventType.APPLICATION, applicationHealthStrategy);
        strategies.put(PublishEventType.TWEET_HASHTAG, individualTweetStrategy);
    }

    public EventStrategy getStrategy(PublishEventType type) {
        return strategies.get(type);
    }
}
