package com.fintech.Bank.audit_dashboard.controller;


import com.fintech.Bank.Account.dtos.AccountDTO;
import com.fintech.Bank.Auth_User.dtos.UserDTO;
import com.fintech.Bank.Transaction.dtos.TransactionDTO;
import com.fintech.Bank.audit_dashboard.service.AuditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AUDITOR')")
public class AuditorController {
//final avoids accidental reassignment
    private final AuditorService auditorService;

    @GetMapping("/totals")
    //RE is the http response sent to the client it has the status code headers and the resep body
    public ResponseEntity<Map<String,Long>>getSystemTotals(){
        return ResponseEntity.ok(auditorService.getSystemTotals());
    }

    @GetMapping("/users")
    public ResponseEntity<UserDTO>findUserByEmail(@RequestParam String email){
        Optional<UserDTO>userDTO = auditorService.findUserByEmail(email);
//if(userDTO.isPresent()){
//    return ResponseEntity.ok(userDTO.get());
//} this is same as below
        return userDTO.map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).build());



    }

    @GetMapping("/accounts")
    public ResponseEntity<AccountDTO>findAccountDetailsByAccountNumber(@RequestParam String accountNumber){
        Optional<AccountDTO>accountDTO = auditorService.findAccountDetailsByAccountNumber(accountNumber);
//if(userDTO.isPresent()){
//    return ResponseEntity.ok(userDTO.get());
//} this is same as below
        return accountDTO.map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).build());



    }

    @GetMapping("/transactions/by-account")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountNumber(
            @RequestParam String accountNumber) {

        List<TransactionDTO> transactionDTOList =
                auditorService.findTransactionsByAccountNumber(accountNumber);

        if (transactionDTOList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(transactionDTOList);
    }

    @GetMapping("/transactions/by-id")
    public ResponseEntity<TransactionDTO> getTransactionById(
            @RequestParam Long id) {

        Optional<TransactionDTO> transactionDTO =
                auditorService.findTransactionById(id);

        return transactionDTO
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
