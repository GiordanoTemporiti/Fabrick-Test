package com.fabrick.orbyta.services;

import com.fabrick.orbyta.dto.AccountTransactionDTO;
import com.fabrick.orbyta.dto.FabrickGeneralDTO;
import com.fabrick.orbyta.exceptions.BadRequestException;
import com.fabrick.orbyta.exceptions.GenericException;
import com.fabrick.orbyta.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    private final DateTimeFormatter formatters;
    private final String baseUrl;
    private final HttpHeaders headers = new HttpHeaders();
    private final HttpEntity<String> entity;
    private final RestTemplate restTemplate = new RestTemplate();

    public AccountService(@Value("${api.fabrick.baseurl}") String baseUrl, @Value("${api.fabrick.authschema}") String authSchema, @Value("${api.fabrick.apikey}") String apiKey, @Value("${api.fabrick.timezone}") String timezone) {
        this.baseUrl = baseUrl;
        this.formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        headers.set("Auth-Schema", authSchema);
        headers.set("Api-Key", apiKey);
        headers.set("X-Time-Zone", timezone);
        headers.setContentType(MediaType.APPLICATION_JSON);

        this.entity = new HttpEntity<>(headers);
    }

    public BigDecimal getBalance(Long accountId) {

        if (accountId == null) throw new BadRequestException("Invalid account ID");

        ResponseEntity<FabrickGeneralDTO> response = restTemplate.exchange(
                String.format("%s/api/gbs/banking/v4.0/accounts/%d/balance", baseUrl, accountId),
                HttpMethod.GET, entity, FabrickGeneralDTO.class
        );

        if (response.getBody() == null) throw new NotFoundException("Account not found");

        Map<String, Object> payload = response.getBody().getPayload();

        if (payload == null || payload.isEmpty()) throw new GenericException("Payload in the response is empty");

        String balanceStr = response.getBody().getPayload().get("balance").toString();

        if (balanceStr == null || balanceStr.isEmpty()) throw new GenericException("Invalid Balance in the response");

        return new BigDecimal(balanceStr);
    }

    public List<AccountTransactionDTO> getTransactionList(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {

        if (accountId == null) throw new BadRequestException("Invalid account ID");

        if (toAccountingDate.isBefore(fromAccountingDate) || fromAccountingDate.isAfter(toAccountingDate))
            throw new BadRequestException("Invalid period");

        final String uri = String.format("%s/api/gbs/banking/v4.0/accounts/%d/transactions?", baseUrl, accountId) +
                String.format("fromAccountingDate=%s", fromAccountingDate.format(formatters)) +
                String.format("&toAccountingDate=%s", toAccountingDate.format(formatters));

        ResponseEntity<FabrickGeneralDTO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, FabrickGeneralDTO.class);

        if (response.getBody() == null) throw new NotFoundException("Account not found");

        Map<String, Object> payload = response.getBody().getPayload();

        if (payload == null || payload.isEmpty()) throw new GenericException("Payload in the response is empty");

        try {
            return ((List<AccountTransactionDTO>) payload.get("list"));
        } catch (Exception e) {
            throw new GenericException("Invalid transactions list in the response");
        }
    }

    public String doMoneyTransfers(Long accountId, String requestBody) {
        if (accountId == null) throw new BadRequestException("Invalid account ID");

        final String uri = String.format("%s/api/gbs/banking/v4.0/accounts/%s/payments/money-transfers", baseUrl, accountId);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForObject(uri, request, String.class);
    }
}
