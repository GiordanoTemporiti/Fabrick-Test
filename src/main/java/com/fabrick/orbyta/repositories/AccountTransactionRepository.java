package com.fabrick.orbyta.repositories;

import com.fabrick.orbyta.models.entities.AccountTansactionEntity;
import com.fabrick.orbyta.models.entities.keys.AccountTansactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTansactionEntity, AccountTansactionId> {
}
