package com.bank.atm.repository;

import com.bank.atm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByMobileNo(String mobileNo);

    boolean existsByMobileNo(String mobileNo);

    Optional<User> findById(String customerId);
}
