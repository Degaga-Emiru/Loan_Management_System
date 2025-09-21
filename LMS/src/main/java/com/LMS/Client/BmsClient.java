<<<<<<< HEAD
package com.LMS.LMS.Client;
// where the bms client logic goes is here
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.LMS.LMS.Model.TransactionHistory;
import com.LMS.LMS.Util.JwtUtil;
import java.math.BigDecimal;
import java.util.*;

@Component
public class BmsClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "https://5452dbc7b418.ngrok-free.app/api/bank";

    private HttpHeaders buildHeaders(String accountNumber) {
        String jwt = JwtUtil.generateToken(accountNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);
        return headers;
    }

    // Verify account (micro deposit initiation)
    public String verifyAccount(String accountNumber) {
        Map<String, String> body = new HashMap<>();
        body.put("accountNumber", accountNumber);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, buildHeaders(accountNumber));
        return restTemplate.postForEntity(baseUrl + "/verify", request, String.class).getBody();
    }

    // Confirm micro deposit amount
    public String confirmMicroDeposit(String accountNumber, BigDecimal amount) {
        Map<String, String> body = new HashMap<>();
        body.put("accountNumber", accountNumber);
        body.put("microDepositAmount", amount.toString());
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, buildHeaders(accountNumber));
        return restTemplate.postForEntity(baseUrl + "/verify-deposit", request, String.class).getBody();
    }

    // Disburse loan
    public String disburseLoan(String accountNumber, BigDecimal loanAmount) {
        Map<String, String> body = new HashMap<>();
        body.put("accountNumber", accountNumber);
        body.put("loanAmount", loanAmount.toString());
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, buildHeaders(accountNumber));
        return restTemplate.postForEntity(baseUrl + "/loan", request, String.class).getBody();
    }

    // Repay loan
    public String repayLoan(String accountNumber, BigDecimal repaymentAmount) {
        Map<String, String> body = new HashMap<>();
        body.put("accountNumber", accountNumber);
        body.put("repaymentAmount", repaymentAmount.toString());
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, buildHeaders(accountNumber));
        return restTemplate.postForEntity(baseUrl + "/repay", request, String.class).getBody();
    }

    // ✅ Fetch transaction history from BMS
    public List<TransactionHistory> getTransactionHistory(String accountNumber) {
        HttpEntity<Void> request = new HttpEntity<>(buildHeaders(accountNumber));
        ResponseEntity<TransactionHistory[]> response = restTemplate.exchange(
                baseUrl + "/transactions?accountNumber=" + accountNumber,
                HttpMethod.GET,
                request,
                TransactionHistory[].class
        );
        return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }
}
=======
package com.LMS.LMS.Client;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.LMS.LMS.Model.TransactionHistory;
import com.LMS.LMS.Util.JwtUtil;

import java.math.BigDecimal;
import java.util.*;

@Component
public class BmsClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "https://5452dbc7b418.ngrok-free.app/api/bank";

    private HttpHeaders buildHeaders(String accountNumber) {
        String jwt = JwtUtil.generateToken(accountNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);
        return headers;
    }

    // Verify account (micro deposit initiation)
    public String verifyAccount(String accountNumber) {
        Map<String, String> body = new HashMap<>();
        body.put("accountNumber", accountNumber);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, buildHeaders(accountNumber));
        return restTemplate.postForEntity(baseUrl + "/verify", request, String.class).getBody();
    }

    // Confirm micro deposit amount
    public String confirmMicroDeposit(String accountNumber, BigDecimal amount) {
        Map<String, String> body = new HashMap<>();
        body.put("accountNumber", accountNumber);
        body.put("microDepositAmount", amount.toString());
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, buildHeaders(accountNumber));
        return restTemplate.postForEntity(baseUrl + "/verify-deposit", request, String.class).getBody();
    }

    // Disburse loan
    public String disburseLoan(String accountNumber, BigDecimal loanAmount) {
        Map<String, String> body = new HashMap<>();
        body.put("accountNumber", accountNumber);
        body.put("loanAmount", loanAmount.toString());
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, buildHeaders(accountNumber));
        return restTemplate.postForEntity(baseUrl + "/loan", request, String.class).getBody();
    }

    // Repay loan
    public String repayLoan(String accountNumber, BigDecimal repaymentAmount) {
        Map<String, String> body = new HashMap<>();
        body.put("accountNumber", accountNumber);
        body.put("repaymentAmount", repaymentAmount.toString());
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, buildHeaders(accountNumber));
        return restTemplate.postForEntity(baseUrl + "/repay", request, String.class).getBody();
    }

    // ✅ Fetch transaction history from BMS
    public List<TransactionHistory> getTransactionHistory(String accountNumber) {
        HttpEntity<Void> request = new HttpEntity<>(buildHeaders(accountNumber));
        ResponseEntity<TransactionHistory[]> response = restTemplate.exchange(
                baseUrl + "/transactions?accountNumber=" + accountNumber,
                HttpMethod.GET,
                request,
                TransactionHistory[].class
        );
        return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }
}
>>>>>>> 6d19046664424a808ba3f3a91443fa40e1897c79
