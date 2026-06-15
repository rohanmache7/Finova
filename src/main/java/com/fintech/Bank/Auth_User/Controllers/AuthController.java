package com.fintech.Bank.Auth_User.Controllers;

import com.fintech.Bank.Auth_User.Services.AuthService;
import com.fintech.Bank.Auth_User.dtos.LoginRequest;
import com.fintech.Bank.Auth_User.dtos.LoginResponse;
import com.fintech.Bank.Auth_User.dtos.RegistrationRequest;
import com.fintech.Bank.Auth_User.dtos.ResetPasswordRequest;
import com.fintech.Bank.Role.entity.Role;
import com.fintech.Bank.res.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authservice;
    @PostMapping("/register")
    public ResponseEntity<Response<String>> createRole(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authservice.register(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authservice.login(loginRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Response<?>> forgotPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(authservice.forgetPassword(resetPasswordRequest.getEmail()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Response<?>> resetPassword(@RequestBody  ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(authservice.updatePasswordviaResetCode(resetPasswordRequest));
    }





}
