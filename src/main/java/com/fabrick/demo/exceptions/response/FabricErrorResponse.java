package com.fabrick.demo.exceptions.response;

import lombok.Value;

@Value
public class FabricErrorResponse {
    String code;
    String description;

    public FabricErrorResponse(String code, String description) {
        this.code = code;
        this.description = description;
    }
}