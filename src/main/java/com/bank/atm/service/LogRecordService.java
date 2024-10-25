package com.bank.atm.service;

import com.bank.atm.DTO.LogRecordDTO;
import com.bank.atm.entity.LogRecord;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface LogRecordService {
    LogRecord createLogRecord(LogRecordDTO logRecordDTO);
    LogRecord updateEndTime(String id);
    List<LogRecordDTO> getAllRecords();
    ResponseEntity<Resource> downloadRecord() throws IOException;

    ResponseEntity<Resource> downloadImage() throws IOException;
}
