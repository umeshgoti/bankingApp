package com.bank.atm.Controller;
import com.bank.atm.entity.User;
import com.bank.atm.enumaration.Roles;
import com.bank.atm.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;


@RestController
@RequestMapping("/api/reset")
public class ResetController {
    private static final Logger logger = LoggerFactory.getLogger(ResetController.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResetController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/resetAll")
    @Transactional
    public String resetAll() {
        insertAll();
        return "reset successfully....";
    }

    private void insertAll() {
        insertUsers();
    }


    private void insertUsers() {
        Optional<User> existingUser = userRepository.findByMobileNo("7567995281");
        if (existingUser.isEmpty()) {
            User superAdmin = new User();
            superAdmin.setFirstname("Admin");
            superAdmin.setLastname("Admin");
            superAdmin.setBalance(100000.0);
            superAdmin.setRole(Roles.ADMIN);
            superAdmin.setMobileNo("7567995281");
            superAdmin.setPin(passwordEncoder.encode("0000"));
            userRepository.save(superAdmin);
            logger.info("Super admin created successfully");
        } else {
            logger.info("Super admin with this email and first name already exists");
        }
    }
}
