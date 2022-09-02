package com.fabrick.demo.exceptions;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class ErrorResponse {
    int status;
    String message;
    long timeStamp;

    public ErrorResponse(HttpStatus status, String message, long timeStamp) {
        this.status = status.value();
        this.message = message;
        this.timeStamp = timeStamp;
    }
}