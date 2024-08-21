package com.data.analytics.client.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record GenericResponse<T>(boolean success, HttpStatus status, T data, String message) {
}
