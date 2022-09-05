package com.fabrick.orbyta.pojo;

import lombok.Value;

@Value
public class FabricError {
    String code;
    String description;

    public FabricError(String code, String description) {
        this.code = code;
        this.description = description;
    }
}