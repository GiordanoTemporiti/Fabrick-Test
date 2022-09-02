package com.fabrick.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
public class FabrickGeneralResponseDTO {
    private String status;
    private ArrayList<Object> errors;
    private Map<String, Object> payload;
}
