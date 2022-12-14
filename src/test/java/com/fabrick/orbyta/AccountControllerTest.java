package com.fabrick.orbyta;

import com.fabrick.orbyta.dto.AccountTransactionDTO;
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

public class AccountControllerTest extends FabrickApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper mapper;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // Checks if returned balance is like expected value
    @Test
    public void checkAccountBalanceValue() throws Exception {
        final long accountId = 14537780;
        final String expectedValue = "149499.37";
        mockMvc.perform(get("/api/v1/accounts/" + accountId + "/balance")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(expectedValue));
    }

    // Checks if each transaction is executed in the specified date range
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

    // Checks if the response API is a bad request
    @Test
    public void checkMoneyTransfersBadRequest() throws Exception {
        final long accountId = 14537780;
        final String requestBody = "{\"creditor\": {\"name\": \"John Doe\",\"account\": {\"accountCode\": \"IT23A0336844430152923804660\",\"bicCode\": \"SELBIT2BXXX\"},\"address\": {\"address\": null,\"city\": null,\"countryCode\": null}},\"executionDate\": \"2019-04-01\",\"uri\": \"REMITTANCE_INFORMATION\",\"description\": \"Payment invoice 75/2017\",\"amount\": 800,\"currency\": \"EUR\",\"isUrgent\": false,\"isInstant\": false,\"feeType\": \"SHA\",\"feeAccountId\": \"45685475\",\"taxRelief\": {\"taxReliefId\": \"L449\",\"isCondoUpgrade\": false,\"creditorFiscalCode\": \"56258745832\",\"beneficiaryType\": \"NATURAL_PERSON\",\"naturalPersonBeneficiary\": {\"fiscalCode1\": \"MRLFNC81L04A859L\",\"fiscalCode2\": null,\"fiscalCode3\": null,\"fiscalCode4\": null,\"fiscalCode5\": null},\"legalPersonBeneficiary\": {\"fiscalCode\": null,\"legalRepresentativeFiscalCode\": null}}}";
        mockMvc.perform(get(String.format("/api/gbs/banking/v4.0/accounts/%s/payments/money-transfers", accountId)).content(requestBody).contentType("application/json"))
                .andExpect(status().is4xxClientError());
    }
}
