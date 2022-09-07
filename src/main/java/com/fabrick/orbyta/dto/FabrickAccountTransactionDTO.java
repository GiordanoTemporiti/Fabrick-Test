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
public class FabrickAccountTransactionDTO extends FabrickGeneralDTO {
    private Map<String, ArrayList<AccountTransactionDTO>> payload;

    public FabrickAccountTransactionDTO(String status, ArrayList<FabricError> errors, Map<String, ArrayList<AccountTransactionDTO>> payload) {
        super(status, errors);
        this.payload = payload;
    }
}
