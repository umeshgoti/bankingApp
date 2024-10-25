package com.bank.atm.repository;

import com.bank.atm.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("SELECT t FROM Transaction t WHERE t.time > :timeThreshold ORDER BY t.time DESC")
    List<Transaction> findTransactionsWithinLast24Hours(@Param("timeThreshold") LocalDateTime timeThreshold);


}
