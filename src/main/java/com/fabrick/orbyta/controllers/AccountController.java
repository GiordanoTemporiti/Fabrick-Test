package com.fabrick.orbyta.controllers;

import com.fabrick.orbyta.dto.AccountTransactionDTO;
import com.fabrick.orbyta.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "/{accountid}/balance", produces = "application/json")
    public BigDecimal getBalance(@PathVariable("accountid") Long accountId) {
        return accountService.getBalance(accountId);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "/{accountid}/transactions", produces = "application/json")
    public List<AccountTransactionDTO> getTransactionList(@PathVariable("accountid") Long accountId,
                                                          @RequestParam("fromAccountingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromAccountingDate,
                                                          @RequestParam("toAccountingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toAccountingDate) {
        return accountService.getTransactionList(accountId, fromAccountingDate, toAccountingDate);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, value = "/{accountid}/payments/money-transfers", produces = "application/json")
    public String moneyTransfers(@PathVariable("accountid") Long accountId,
                                 @RequestParam("receiverName") String receiverName,
                                 @RequestParam("description") String description,
                                 @RequestParam("currency") String currency,
                                 @RequestParam("amount") String amount,
                                 @RequestParam("executionDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate executionDate) {
        return accountService.doMoneyTransfers(accountId, receiverName, description, currency, amount, executionDate);
    }
}
