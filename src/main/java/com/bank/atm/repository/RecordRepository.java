package com.bank.atm.repository;

import com.bank.atm.entity.Atm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, String> {
}
