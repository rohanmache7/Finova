package com.fintech.Bank.Auth_User.Services;

import com.fintech.Bank.Auth_User.dtos.LoginRequest;
import com.fintech.Bank.Auth_User.dtos.LoginResponse;
import com.fintech.Bank.Auth_User.dtos.RegistrationRequest;
import com.fintech.Bank.Auth_User.dtos.ResetPasswordRequest;
import com.fintech.Bank.res.Response;

public interface AuthService {
    Response<String>register(RegistrationRequest registrationRequest);
    Response<LoginResponse>login(LoginRequest loginRequest);
    Response<?>forgetPassword(String email);
    Response<?>updatePasswordviaResetCode(ResetPasswordRequest resetPasswordRequest);



}
