package com.bank.atm.service;

import com.bank.atm.DTO.RegisterRequestDTO;
import com.bank.atm.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    String login(User user);

    User signUp(RegisterRequestDTO registerRequestDTO);
}
