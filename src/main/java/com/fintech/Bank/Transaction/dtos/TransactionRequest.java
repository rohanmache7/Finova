package com.fintech.Bank.Transaction.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fintech.Bank.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequest {
    private TransactionType transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String description;

    private String destinationAccountNumber;//if its a transfer we will need it



}
