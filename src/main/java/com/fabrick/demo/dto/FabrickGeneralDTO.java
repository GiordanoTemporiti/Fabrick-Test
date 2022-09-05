package com.fabrick.demo.dto;

import com.fabrick.demo.exceptions.response.FabricErrorResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
public class FabrickGeneralDTO {
    private String status;
    private ArrayList<FabricErrorResponse> errors;
    private Map<String, Object> payload;
}
