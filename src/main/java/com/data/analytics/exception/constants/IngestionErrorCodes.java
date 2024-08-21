package com.data.analytics.exception.constants;

public enum IngestionErrorCodes {

    INGESTION_ERROR("I001", "Error in Ingestion"),
    MESSAGE_CONVERSION_ERROR("E002", "Error occurred while message conversion");
    private final String code;
    private final String message;

    IngestionErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
