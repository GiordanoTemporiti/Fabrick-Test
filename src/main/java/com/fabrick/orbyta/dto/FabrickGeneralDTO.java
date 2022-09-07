package com.fabrick.orbyta.dto;

import com.fabrick.orbyta.pojo.FabricError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class FabrickGeneralDTO {
    protected String status;
    protected ArrayList<FabricError> errors;
}
