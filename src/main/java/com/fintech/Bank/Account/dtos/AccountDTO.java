package com.fintech.Bank.Account.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fintech.Bank.Auth_User.dtos.UserDTO;
import com.fintech.Bank.Auth_User.entity.User;
import com.fintech.Bank.Transaction.dtos.TransactionDTO;
import com.fintech.Bank.enums.AccountStatus;
import com.fintech.Bank.enums.AccountType;
import com.fintech.Bank.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Long id;


    private String accountNumber;

    private BigDecimal balance = BigDecimal.ZERO;


    private AccountType accountType;

    @JsonBackReference
    private UserDTO user;


    private Currency currency;


    private AccountStatus accountStatus;


  @JsonManagedReference
    private List<TransactionDTO> transactions;


    private LocalDateTime closedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;





}
