package com.fabrick.orbyta.models.entities.keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountTansactionId implements Serializable {
    @Column(name = "account_id", nullable = false)
    private Long accountId;
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;
    @Column(name = "operation_id", nullable = false)
    private String operationId;
}
