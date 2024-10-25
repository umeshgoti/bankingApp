package com.bank.atm.service.impl;

import com.bank.atm.DTO.RegisterRequestDTO;
import com.bank.atm.entity.User;
import com.bank.atm.enumaration.Roles;
import com.bank.atm.repository.UserRepository;
import com.bank.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public UserDetails loadUserByUsername(String mobileNo) throws UsernameNotFoundException {
        Optional<User> optUser = userRepo.findByMobileNo(mobileNo);
        User user = optUser.orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + mobileNo));
        return new UserDetailsImpl(user.getMobileNo(), user.getPin(), user.getRole());
    }

    @Override
    public String login(User user) {
        return "login";
    }

    @Override
    public User signUp(RegisterRequestDTO registerRequestDTO) {
        Optional<User> byMobileNo = userRepo.findByMobileNo(registerRequestDTO.getMobileNo());
        if (byMobileNo.isPresent()) {
            throw new UsernameNotFoundException("user already registered : " + registerRequestDTO.getMobileNo());
        } else {
            User user = new User();
            user.setMobileNo(registerRequestDTO.getMobileNo());
            user.setPin(passwordEncoder.encode(registerRequestDTO.getPin()));
            user.setRole(Roles.CUSTOMER);
            user.setFirstname(registerRequestDTO.getFirstName());
            user.setBalance(registerRequestDTO.getBalance());
            user.setLastname(registerRequestDTO.getLastName());
            return userRepo.save(user);
        }
    }

    @Override
    public List<RegisterRequestDTO> getAllCustomer() {
        List<User> all = userRepo.findAllOrderByCreatedDateDesc();
        List<RegisterRequestDTO> dtos = new ArrayList<>();
        for (User user : all) {
            RegisterRequestDTO dto = new RegisterRequestDTO();
            dto.setFirstName(user.getFirstname());
            dto.setLastName(user.getLastname());
            dto.setMobileNo(user.getMobileNo());
            dto.setBalance(user.getBalance());
            dtos.add(dto);
        }
        return dtos;
    }

}
