package com.example.wamitest.constants;

public enum LoggingConstants {
    LOG_DEBUG_EMPTY_PATTERN("Method was called."),
    LOG_DEBUG_ONE_ARG_PATTERN("Method was called with argument: '{}'."),
    LOG_DEBUG_TWO_ARG_PATTERN("Method was called with argument(s): LogFile: '{}', id: '{}'.");

    private final String message;

    LoggingConstants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
