package com.bank.atm.service.impl;

import com.bank.atm.DTO.LogRecordDTO;
import com.bank.atm.Exception.ResourceNotFoundException;
import com.bank.atm.entity.LogRecord;
import com.bank.atm.repository.LogRecordRepository;
import com.bank.atm.service.LogRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogRecordServiceImpl implements LogRecordService {

    private final LogRecordRepository logRecordRepository;

    @Autowired
    public LogRecordServiceImpl(LogRecordRepository logRecordRepository) {
        this.logRecordRepository = logRecordRepository;
    }

    @Override
    public LogRecord createLogRecord(LogRecordDTO logRecordDTO) {
        LogRecord logRecord = LogRecordDTO.toEntity(logRecordDTO);
        return logRecordRepository.save(logRecord);
    }

    @Override
    public LogRecord updateEndTime(String id) {
        LogRecord logRecord = logRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record"));
        logRecord.setEndTime(LocalDateTime.now());
        return logRecordRepository.save(logRecord);
    }

    @Override
    public List<LogRecordDTO> getAllRecords() {
        return logRecordRepository.findAll().stream()
                .map(LogRecordDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public LogRecordDTO downloadRecord(String id) {
        LogRecord logRecord = logRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        return LogRecordDTO.fromEntity(logRecord);
    }
}
