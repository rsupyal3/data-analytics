package com.data.analytics.exception;

import com.data.analytics.exception.constants.IngestionErrorCodes;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

@Getter
public class IngestionException extends RuntimeException {

    private final String message;
    private final IngestionErrorCodes code;
    private final Map<String, Object> errorContext;

    private IngestionException(IngestionErrorCodes code, String message) {
        this.code = code;
        this.message = message;
        this.errorContext = new HashMap<>();
    }

    private IngestionException(IngestionErrorCodes code, String message, Pair<String, Object> context) {
        this.code = code;
        this.message = message;
        this.errorContext = new HashMap<>();
        this.errorContext.put(context.getKey(), context.getValue());
    }

    private IngestionException(IngestionErrorCodes code, String message, Throwable e) {
        super(message, e);
        this.code = code;
        this.message = message;
        this.errorContext = new HashMap<>();
    }

    public static IngestionException of(IngestionErrorCodes code, String message) {
        return new IngestionException(code, message);
    }

    public static IngestionException of(IngestionErrorCodes code) {
        return of(code, code.getMessage());
    }

    public static IngestionException of(IngestionErrorCodes code, String message, Pair<String, Object> context) {
        return new IngestionException(code, message, context);
    }

    public static IngestionException propagate(IngestionErrorCodes code, String message, Throwable cause) {
        return new IngestionException(code, message, cause);
    }

    public static IngestionException propagate(IngestionErrorCodes code, String message) {
        return new IngestionException(code, message);
    }
}