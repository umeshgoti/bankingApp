package com.bank.atm.repository;

import com.bank.atm.entity.LogRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRecordRepository extends JpaRepository<LogRecord, String> {
}
