package com.fintech.Bank.audit_dashboard.service;

import com.fintech.Bank.Account.dtos.AccountDTO;
import com.fintech.Bank.Auth_User.dtos.UserDTO;
import com.fintech.Bank.Transaction.dtos.TransactionDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AuditorService {

    Map<String,Long> getSystemTotals();
    //Optional is used when the container may or may not contain a value

    //Find a user using the email and return a userDTO if it exists
    Optional<UserDTO> findUserByEmail(String email);
    Optional<AccountDTO>findAccountDetailsByAccountNumber(String accountNumber);
    List<TransactionDTO>findTransactionsByAccountNumber(String accountNumber);
    Optional<TransactionDTO>findTransactionById(Long transactionId);

}
