package com.fintech.Bank.Account.Controllers;

import com.fintech.Bank.Account.Services.AccountService;
import com.fintech.Bank.res.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/me")
    public ResponseEntity<Response<?>>getMyAccounts(){
        return ResponseEntity.ok(accountService.getMyAccounts());

    }

    @DeleteMapping("/close/{accountNumber}")
    public ResponseEntity<Response<?>>closeAccount(@PathVariable String accountNumber){
        return ResponseEntity.ok(accountService.closeAccount(accountNumber));
    }
}
