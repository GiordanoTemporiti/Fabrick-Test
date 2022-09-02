package com.fabrick.demo.controllers;

import com.fabrick.demo.dto.AccountTransactionDTO;
import com.fabrick.demo.services.AccountService;
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
    public List<AccountTransactionDTO> getTransactionList(@PathVariable("accountid") Long accountId, @RequestParam("fromAccountingDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromAccountingDate, @RequestParam("toAccountingDate")
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toAccountingDate) {
        return accountService.getTransactionList(accountId, fromAccountingDate, toAccountingDate);
    }
}
