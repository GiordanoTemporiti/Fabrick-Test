package com.fabrick.demo.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    public BigDecimal getBalance(Long accountId) {
        return new BigDecimal(0);
    }
}
