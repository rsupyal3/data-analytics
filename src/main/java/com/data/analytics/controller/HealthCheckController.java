package com.data.analytics.controller;

import com.data.analytics.client.dto.response.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthCheckController {
    @GetMapping("/health")
    @Operation(summary = "Health Check api")
    public GenericResponse<String> healthCheck() {
        return GenericResponse.<String>builder()
                .status(HttpStatus.OK)
                .success(true)
                .data("Healthy")
                .build();
    }
}
