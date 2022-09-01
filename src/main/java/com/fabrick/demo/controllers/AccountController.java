package com.fabrick.demo.controllers;

import com.fabrick.demo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "/{accountid}/balance", produces = "application/json")
    public BigDecimal getAccountBalance(@PathVariable("accountid") Long accountId) {
        return accountService.getBalance(accountId);
    }

}
