package com.fabrick.demo;

import com.fabrick.demo.dto.AccountTransactionDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends DemoApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper mapper;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void checkAccountBalanceValue() throws Exception {
        final long accountId = 14537780;
        final String expectedValue = "149499.37";
        mockMvc.perform(get("/api/v1/accounts/" + accountId + "/balance")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(expectedValue));
    }

    @Test
    public void checkTransactionListIsInRange() throws Exception {
        final long accountId = 14537780;
        final String fromAccountingDateStr = "2019-01-01";
        final String toAccountingDateStr = "2019-12-01";
        final LocalDate fromAccountingDate = LocalDate.parse(fromAccountingDateStr);
        final LocalDate toAccountingDate = LocalDate.parse(toAccountingDateStr);
        MvcResult result = mockMvc.perform(get("/api/v1/accounts/" + accountId + "/transactions?fromAccountingDate=" + fromAccountingDateStr + "&toAccountingDate=" + toAccountingDateStr)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        List<AccountTransactionDTO> resultList = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        for (AccountTransactionDTO currentTransaction : resultList) {
            LocalDate currentDate = currentTransaction.getAccountingDate();
            Assert.assertTrue(
                    (currentDate.isAfter(fromAccountingDate) || currentDate.isEqual(fromAccountingDate)) &&
                            (currentDate.isBefore(toAccountingDate) || currentDate.isEqual(toAccountingDate))
            );
        }
    }

}
