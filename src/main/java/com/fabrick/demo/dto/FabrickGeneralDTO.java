package com.fabrick.demo.dto;

import com.fabrick.demo.pojo.FabricError;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
public class FabrickGeneralDTO {
    private String status;
    private ArrayList<FabricError> errors;
    private Map<String, Object> payload;
}
