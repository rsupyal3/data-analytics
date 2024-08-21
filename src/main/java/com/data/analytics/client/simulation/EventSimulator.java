package com.data.analytics.client.simulation;

import com.data.analytics.client.dto.Event;
import com.data.analytics.client.dto.events.ApplicationHealthEvent;
import com.data.analytics.client.dto.events.ContentPreferenceEvent;
import com.data.analytics.client.dto.events.TweetEvent;
import com.data.analytics.client.dto.enums.PublishEventType;
import com.data.analytics.client.dto.events.ViewerInteractionEvent;
import com.data.analytics.client.strategy.impl.ApplicationHealthStrategy;
import com.data.analytics.client.strategy.impl.ContentPreferenceStrategy;
import com.data.analytics.client.strategy.impl.IndividualTweetStrategy;
import com.data.analytics.client.strategy.impl.ViewerInteractionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class EventSimulator {
    private final ApplicationHealthStrategy applicationHealthStrategy;
    private final ViewerInteractionStrategy viewerInteractionStrategy;
    private final ContentPreferenceStrategy contentPreferenceStrategy;
    private final IndividualTweetStrategy individualTweetStrategy;

    private final String[] viewerInteractions = {"click", "play", "pause", "stop", "like", "dislike"};
    private final String[] movies = {"Movie1", "Movie2", "Movie3", "Movie4", "Movie5"};
    private final String[] devices = {"Mobile", "Tablet", "PC", "TV"};
    private final String[] genres = {"Action", "Comedy", "Drama", "Horror", "Sci-Fi"};
    private final String[] services = {"ServiceA", "ServiceB", "ServiceC", "ServiceD"};
    private final String[] metrics = {"CPU_Usage", "Memory_Usage", "Disk_Usage"};
    private final String[] hashtags = {"#ElectricCars", "#TechNews", "#AI", "#ClimateChange", "#Sports"};

    private final Random random = new Random();

    @Autowired
    public EventSimulator(ApplicationHealthStrategy applicationHealthStrategy,
                          ViewerInteractionStrategy viewerInteractionStrategy,
                          ContentPreferenceStrategy contentPreferenceStrategy,
                          IndividualTweetStrategy individualTweetStrategy) {
        this.applicationHealthStrategy = applicationHealthStrategy;
        this.viewerInteractionStrategy = viewerInteractionStrategy;
        this.contentPreferenceStrategy = contentPreferenceStrategy;
        this.individualTweetStrategy = individualTweetStrategy;
    }


    @Scheduled(fixedRate = 5000)
    public void simulateIndividualTweetEvents() {
        Event event = Event.builder()
                .type(PublishEventType.TWEET_HASHTAG)
                .topic(PublishEventType.TWEET_HASHTAG.getTopic())
                .build();
        TweetEvent tweetEvent = TweetEvent.builder()
                .tweetId(String.valueOf(random.nextInt(10000)))
                .hashTag(hashtags[random.nextInt(hashtags.length)])
                .timestamp(System.currentTimeMillis())
                .build();
        event.setPayload(tweetEvent);
        individualTweetStrategy.processEvent(event);
    }
    @Scheduled(fixedRate = 15000)
    public void simulateViewerInteraction() {
        Event event = Event.builder()
                .type(PublishEventType.VIEWER)
                .topic(PublishEventType.VIEWER.getTopic())
                .build();
        ViewerInteractionEvent viewerInteractionEvent = ViewerInteractionEvent.builder()
                .userId("user"+ random.nextInt(100))
                .movie(movies[random.nextInt(movies.length)])
                .action(viewerInteractions[random.nextInt(viewerInteractions.length)])
                .device(devices[random.nextInt(devices.length)])
                .timestamp(System.currentTimeMillis())
                .build();
        event.setPayload(viewerInteractionEvent);
         viewerInteractionStrategy.processEvent(event);
    }

    @Scheduled(fixedRate = 15000)
    public void simulateContentPreference() {
        Event event = Event.builder()
                .type(PublishEventType.CONTENT)
                .topic(PublishEventType.CONTENT.getTopic())
                .build();
        ContentPreferenceEvent contentPreferenceEvent = ContentPreferenceEvent.builder()
                .userId("user"+ random.nextInt(100))
                .genre(genres[random.nextInt(genres.length)])
                .rating(random.nextInt(5) + 1)
                .timestamp(System.currentTimeMillis())
                .build();
        event.setPayload(contentPreferenceEvent);
         contentPreferenceStrategy.processEvent(event);
    }

    @Scheduled(fixedRate = 15000)
    public void applicationHealthMetrics() {
        Event event = Event.builder()
                .type(PublishEventType.APPLICATION)
                .topic(PublishEventType.APPLICATION.getTopic())
                .build();
        ApplicationHealthEvent applicationHealthEvent = ApplicationHealthEvent.builder()
                .service(services[random.nextInt(services.length)])
                .metric(metrics[random.nextInt(metrics.length)])
                .value(random.nextInt(100))
                .timestamp(System.currentTimeMillis())
                .build();
        event.setPayload(applicationHealthEvent);
        applicationHealthStrategy.processEvent(event);
    }
}
