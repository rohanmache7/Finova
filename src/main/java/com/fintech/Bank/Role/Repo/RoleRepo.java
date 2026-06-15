package com.fintech.Bank.Role.Repo;

import com.fintech.Bank.Role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);

}
