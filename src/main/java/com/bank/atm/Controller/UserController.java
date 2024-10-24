package com.bank.atm.Controller;

import com.bank.atm.DTO.LoginRequest;
import com.bank.atm.DTO.RegisterRequestDTO;
import com.bank.atm.entity.User;
import com.bank.atm.security.JwtUtil;
import com.bank.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getMobileNo(), loginRequest.getPin()));
            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getMobileNo());
            String jwt = jwtUtil.generateToken(userDetails);
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestBody RegisterRequestDTO registerRequestDTO) {
        User user = userService.signUp(registerRequestDTO);
        return "User registered successfully with ID: " + user.getId();
    }

}
