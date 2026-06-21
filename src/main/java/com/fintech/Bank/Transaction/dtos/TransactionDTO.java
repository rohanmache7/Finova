package com.fintech.Bank.Transaction.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fintech.Bank.Account.dtos.AccountDTO;
import com.fintech.Bank.enums.TransactionStatus;
import com.fintech.Bank.enums.TransactionType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    @Id
    private Long id;


    private BigDecimal amount;


    private TransactionType transactionType;


    private LocalDateTime transactionDate = LocalDateTime.now();

    private String description;


    private TransactionStatus status;


  @JsonBackReference
    private AccountDTO account;

    //for transfer
    private String sourceAccount;
    private String destinationAccount;





}

