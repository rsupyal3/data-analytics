package com.data.analytics.dto;

public enum EventStatus {
    NEW,          // Event has just been created or received
    ENRICHED,     // Event has been enriched with additional information
    PROCESSED,    // Event has been successfully processed
    FAILED,       // Event processing has failed
    PENDING,      // Event is pending for further actions
    COMPLETED;    // Event processing is fully completed

    @Override
    public String toString() {
        return switch (this) {
            case NEW -> "NEW";
            case ENRICHED -> "ENRICHED";
            case PROCESSED -> "PROCESSED";
            case FAILED -> "FAILED";
            case PENDING -> "PENDING";
            case COMPLETED -> "COMPLETED";
        };
    }
}
