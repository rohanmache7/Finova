package com.fintech.Bank.security;

import com.fintech.Bank.Auth_User.Repo.UserRepo;
import com.fintech.Bank.Auth_User.entity.User;
import com.fintech.Bank.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {
private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username).orElseThrow(()-> new NotFoundException("Email Not found"));


        return AuthUser.builder().user(user).build();
    }

}
