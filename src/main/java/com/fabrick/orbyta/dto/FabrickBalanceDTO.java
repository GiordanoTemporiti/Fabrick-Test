package com.fabrick.orbyta.dto;

import com.fabrick.orbyta.pojo.FabricError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
public class FabrickBalanceDTO extends FabrickGeneralDTO {
    private Map<String, Object> payload;

    public FabrickBalanceDTO(String status, ArrayList<FabricError> errors, Map<String, Object> payload) {
        super(status, errors);
        this.payload = payload;
    }
}
