package com.fabrick.orbyta.exceptions.response;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class DefaultErrorResponse {
    int status;
    String message;
    long timeStamp;

    public DefaultErrorResponse(HttpStatus status, String message, long timeStamp) {
        this.status = status.value();
        this.message = message;
        this.timeStamp = timeStamp;
    }
}