package com.fintech.Bank.Auth_User.Services.impl;

import com.fintech.Bank.Account.Services.AccountService;
import com.fintech.Bank.Account.entity.Account;
import com.fintech.Bank.Auth_User.Repo.PasswordResetCodeRepo;
import com.fintech.Bank.Auth_User.Repo.UserRepo;
import com.fintech.Bank.Auth_User.Services.AuthService;
import com.fintech.Bank.Auth_User.Services.CodeGenerator;
import com.fintech.Bank.Auth_User.dtos.LoginRequest;
import com.fintech.Bank.Auth_User.dtos.LoginResponse;
import com.fintech.Bank.Auth_User.dtos.RegistrationRequest;
import com.fintech.Bank.Auth_User.dtos.ResetPasswordRequest;
import com.fintech.Bank.Auth_User.entity.PasswordResetCode;
import com.fintech.Bank.Auth_User.entity.User;
import com.fintech.Bank.Notification.Services.NotificationService;
import com.fintech.Bank.Notification.dtos.NotificationDTO;
import com.fintech.Bank.Role.Repo.RoleRepo;
import com.fintech.Bank.Role.entity.Role;
import com.fintech.Bank.enums.AccountType;
import com.fintech.Bank.enums.Currency;
import com.fintech.Bank.exceptions.BadRequestException;
import com.fintech.Bank.exceptions.NotFoundException;
import com.fintech.Bank.res.Response;
import com.fintech.Bank.security.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final NotificationService notificationService;
    private final CodeGenerator codeGenerator;
    private final PasswordResetCodeRepo passwordResetCodeRepo;
    private final AccountService accountService;

    @Value("${password.reset.link}")
    private String resetLink;

    @Override
    public Response<String> register(RegistrationRequest registrationRequest) {
        List<Role> roles;

        if(registrationRequest.getRoles()==null || registrationRequest.getRoles().isEmpty()){
            Role defaultRole = roleRepo.findByName("CUSTOMER").orElseThrow(()->new NotFoundException("CUSTOMER Role Not Found"));
            roles = Collections.singletonList(defaultRole);
        }
        else{
            roles = registrationRequest.getRoles().stream().map(roleName->roleRepo.findByName(roleName).orElseThrow(()-> new NotFoundException("Role Not Found"+roleName))).collect(Collectors.toList());
        }

        if(userRepo.findByEmail(registrationRequest.getEmail()).isPresent()){
            throw new BadRequestException("Email Already In use");
        }
        User user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(roles).active(true)
                .build();
      User savedUser = userRepo.save(user);
//AUTO Generate  account number for the user
        Account savedAccount = accountService.createAccount(AccountType.SAVINGS,savedUser);
//Account savedAccount = accountService.createAccount(AccountType.SAVINGS,savedUser);
//Send Welcome Email
Map<String,Object> vars = new HashMap<>();
vars.put("name",savedUser.getFirstName());

        NotificationDTO notificationDTO = NotificationDTO.builder()
                .recipient(savedUser.getEmail())
                .subject("Welcome to Finova Bank")
                .templateName("welcome")
                .templateVariables(vars)
                .build();

        notificationService.sendEmail(notificationDTO,savedUser);


//send account creation details

Map<String,Object>accountVars = new HashMap<>();
accountVars.put("name",savedUser.getFirstName());
accountVars.put("accountNumber",savedAccount.getAccountNumber());
accountVars.put("accountType",AccountType.SAVINGS.name());
accountVars.put("currency", Currency.USD);

        NotificationDTO accountCreatedEmail = NotificationDTO.builder()
                .recipient(savedUser.getEmail())
                .subject("You new Bank Account has Been Created")
                .templateName("account-created")
                .templateVariables(accountVars)
                .build();

        notificationService.sendEmail(accountCreatedEmail,savedUser);


        return Response.<String>builder()
                .statusCode(200)
                .message("User registered successfully")
                .data("Email of your account details has been sent to you.Your Account Number is: "+savedAccount.getAccountNumber())
                .build ();
    }

    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        User user = userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("Email not Found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Password is incorrect");
        }
        String token = tokenService.generateToken(user.getEmail());

        LoginResponse loginResponse = LoginResponse.builder().roles(user.getRoles().stream().map(Role::getName).toList()).token(token).build();

        return Response.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login Successfull")
                .data(loginResponse)
                .build();
    }
    @Override
    @Transactional
    public Response<?> forgetPassword(String email) {
        User user  = userRepo.findByEmail(email).orElseThrow(()-> new NotFoundException("User not Found"));
        passwordResetCodeRepo.deleteByUserId(user.getId());

        String code = codeGenerator.generatedUniqueCode();

        PasswordResetCode resetCode = PasswordResetCode.builder().
                user(user).code(code).expiryDate(calculateExpiryDate()).used(false).build();

        passwordResetCodeRepo.save(resetCode);

        //send email reset link

        Map<String,Object>templateVariables = new HashMap<>();
        templateVariables.put("name",user.getFirstName());
        templateVariables.put("resetLink",resetLink+code);

        NotificationDTO notificationDTO = NotificationDTO.builder()
                .recipient(user.getEmail())
                .subject("Password Reset Code")
                .templateName("password-reset")
                .templateVariables(templateVariables)
                .build();

        notificationService.sendEmail(notificationDTO,user);

      return Response.builder()
              .statusCode(HttpStatus.OK.value())
              .message("Password reset code sent to your email")
              .build();

    }

    @Override
    @Transactional
    public Response<?> updatePasswordviaResetCode(ResetPasswordRequest resetPasswordRequest) {
    String code = resetPasswordRequest.getCode();
    String newPassword = resetPasswordRequest.getNewPassword();

    PasswordResetCode resetCode = passwordResetCodeRepo.findByCode(code).orElseThrow(()->new BadRequestException("Invalid Reset Code"));

    if(resetCode.getExpiryDate().isBefore(LocalDateTime.now())){
        passwordResetCodeRepo.delete(resetCode);
        throw new BadRequestException("Reset code has expired");
    }

    User user = resetCode.getUser();
    user.setPassword(passwordEncoder.encode(newPassword));
   userRepo.save(user);
   passwordResetCodeRepo.delete(resetCode);

   //Send confirmation mail
        Map<String,Object>templateVariables = new HashMap<>();
        templateVariables.put("name",user.getFirstName());

        NotificationDTO confirmationEmail = NotificationDTO.builder().recipient(user.getEmail()).subject("Password updated successfully").templateName("password-update-confirmation").templateVariables(templateVariables).build();

        notificationService.sendEmail(confirmationEmail,user);


        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Password updated successfully")
                .build();

    }



    private LocalDateTime calculateExpiryDate(){
        return LocalDateTime.now().plusHours(5);
    }
}
