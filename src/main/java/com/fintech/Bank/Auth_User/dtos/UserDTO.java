package com.fintech.Bank.Auth_User.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fintech.Bank.Account.dtos.AccountDTO;
import com.fintech.Bank.Role.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {


    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;



    private String email;
    @JsonIgnore
    private String password;
    private String profilePictureUrl;
    private boolean active = true;


    private List<Role>roles;

    @JsonManagedReference
    private List<AccountDTO>accounts;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
