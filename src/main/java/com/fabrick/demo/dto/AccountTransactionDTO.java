package com.fabrick.demo.dto;

import com.fabrick.demo.pojo.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccountTransactionDTO {
    private String transactionId;
    private String operationId;
    private LocalDate accountingDate;
    private LocalDate valueDate;
    private TransactionType type;
    private BigDecimal amount;
    private String currency;
    private String description;
}