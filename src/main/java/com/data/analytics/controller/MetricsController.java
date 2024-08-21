package com.data.analytics.controller;

import com.data.analytics.client.dto.Event;
import com.data.analytics.client.dto.response.GenericResponse;
import com.data.analytics.client.factory.EventStrategyFactory;
import com.data.analytics.client.strategy.EventStrategy;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Validated
public class MetricsController {
    private final EventStrategyFactory eventStrategyFactory;

    @Autowired
    public MetricsController(EventStrategyFactory eventStrategyFactory) {
        this.eventStrategyFactory = eventStrategyFactory;
    }

    @PostMapping("/events")
    @Operation(summary = "Publish events")
    public GenericResponse<String> sendMessage(@RequestBody Event event) {
        EventStrategy strategy = eventStrategyFactory.getStrategy(event.getType());
        strategy.processEvent(event);
        return GenericResponse.<String>builder()
                .status(HttpStatus.OK)
                .success(true)
                .message("Message Published")
                .build();
    }
}
