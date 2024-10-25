package com.bank.atm.repository;

import com.bank.atm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByMobileNo(String mobileNo);

    boolean existsByMobileNo(String mobileNo);

    Optional<User> findById(String customerId);

    @Query("SELECT u FROM User u ORDER BY u.createdDate DESC")
    List<User> findAllOrderByCreatedDateDesc();
}
