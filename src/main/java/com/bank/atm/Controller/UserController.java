package com.bank.atm.Controller;

import com.bank.atm.DTO.ApiResponse;
import com.bank.atm.DTO.LoginRequest;
import com.bank.atm.DTO.LoginResponseDTO;
import com.bank.atm.DTO.RegisterRequestDTO;
import com.bank.atm.entity.User;
import com.bank.atm.repository.UserRepository;
import com.bank.atm.security.JwtUtil;
import com.bank.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getMobileNo(), loginRequest.getPin()));
            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getMobileNo());
            String jwt = jwtUtil.generateToken(userDetails);
            User user = userRepository.findByMobileNo(loginRequest.getMobileNo()).get();
            LoginResponseDTO responseData = new LoginResponseDTO(user.getId(), jwt);
            return ResponseEntity.ok(new ApiResponse<>("Login successful", HttpStatus.OK.value(), responseData));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signup(@RequestBody RegisterRequestDTO registerRequestDTO) {
        try {
            User user = userService.signUp(registerRequestDTO);
            return ResponseEntity.ok(new ApiResponse<>("User registered successfully with ID: " + user.getId(), HttpStatus.OK.value(), user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }
    
}
