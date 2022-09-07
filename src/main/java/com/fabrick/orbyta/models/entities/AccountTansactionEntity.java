package com.fabrick.orbyta.models.entities;

import com.fabrick.orbyta.dto.AccountTransactionDTO;
import com.fabrick.orbyta.models.entities.keys.AccountTansactionId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_transaction")
public class AccountTansactionEntity {
    @EmbeddedId
    private AccountTansactionId id;
    private LocalDate accountingDate;
    private LocalDate valueDate;
    private String type;
    private BigDecimal amount;
    private String currency;
    private String description;

    public AccountTansactionEntity(Long accountId, AccountTransactionDTO accountTransactionDTO) {
        this.id = new AccountTansactionId(accountId, accountTransactionDTO.getTransactionId(), accountTransactionDTO.getOperationId());
        this.accountingDate = accountTransactionDTO.getAccountingDate();
        this.valueDate = accountTransactionDTO.getValueDate();
        this.type = accountTransactionDTO.getType().getValue();
        this.amount = accountTransactionDTO.getAmount();
        this.currency = accountTransactionDTO.getCurrency();
        this.description = accountTransactionDTO.getDescription();
    }
}
