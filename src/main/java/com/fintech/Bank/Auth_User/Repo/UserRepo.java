package com.fintech.Bank.Auth_User.Repo;

import com.fintech.Bank.Auth_User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

}
