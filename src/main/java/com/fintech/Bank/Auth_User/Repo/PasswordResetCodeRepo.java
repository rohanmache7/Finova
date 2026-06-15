package com.fintech.Bank.Auth_User.Repo;

import com.fintech.Bank.Auth_User.entity.PasswordResetCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetCodeRepo extends JpaRepository<PasswordResetCode,Long> {

    Optional<PasswordResetCode>findByCode(String code);
    void deleteByUserId(Long userId);

}
