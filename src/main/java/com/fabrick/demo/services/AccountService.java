package com.fabrick.demo.services;

import com.fabrick.demo.dto.AccountTransactionDTO;
import com.fabrick.demo.dto.FabrickGeneralResponseDTO;
import com.fabrick.demo.exceptions.BadRequestException;
import com.fabrick.demo.exceptions.GenericException;
import com.fabrick.demo.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    @Value("${api.fabrick.baseurl}")
    private String baseUrl;
    @Value("${api.fabrick.authschema}")
    public String authSchema;
    @Value("${api.fabrick.apikey}")
    public String apiKey;

    public BigDecimal getBalance(Long accountId) {

        if (accountId == null) throw new BadRequestException("Invalid account ID");

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Auth-Schema", authSchema);
        headers.set("Api-Key", apiKey);

        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FabrickGeneralResponseDTO> response = restTemplate.exchange(
                baseUrl + String.format("/api/gbs/banking/v4.0/accounts/%d/balance", accountId),
                HttpMethod.GET, entity, FabrickGeneralResponseDTO.class
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

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-d");

        String uri = baseUrl + String.format("/api/gbs/banking/v4.0/accounts/%d/transactions?", accountId) +
                String.format("fromAccountingDate=%s", fromAccountingDate.format(formatters)) +
                String.format("&toAccountingDate=%s", toAccountingDate.format(formatters));

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Auth-Schema", authSchema);
        headers.set("Api-Key", apiKey);

        final HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FabrickGeneralResponseDTO> response = restTemplate.exchange(uri, HttpMethod.GET, entity, FabrickGeneralResponseDTO.class);

        if (response.getBody() == null) throw new NotFoundException("Account not found");

        Map<String, Object> payload = response.getBody().getPayload();

        if (payload == null || payload.isEmpty()) throw new GenericException("Payload in the response is empty");

        try {
            return ((List<AccountTransactionDTO>) payload.get("list"));
        } catch (Exception e) {
            throw new GenericException("Invalid transactions list in the response");
        }
    }
}
