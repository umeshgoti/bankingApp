package com.bank.atm.service;

import com.bank.atm.DTO.LogRecordDTO;
import com.bank.atm.entity.LogRecord;

import java.util.List;

public interface LogRecordService {
    LogRecord createLogRecord(LogRecordDTO logRecordDTO);
    LogRecord updateEndTime(String id);
    List<LogRecordDTO> getAllRecords();
    LogRecordDTO downloadRecord(String id);
}
