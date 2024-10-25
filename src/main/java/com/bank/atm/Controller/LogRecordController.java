package com.bank.atm.Controller;

import com.bank.atm.DTO.ApiResponse;
import com.bank.atm.DTO.LogRecordDTO;
import com.bank.atm.entity.LogRecord;
import com.bank.atm.enumaration.RecordType;
import com.bank.atm.service.LogRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/log-records")
public class LogRecordController {

    @Autowired
    private LogRecordService logRecordService;


    @PostMapping
    public ResponseEntity<ApiResponse<String>> createLogRecord(@RequestBody LogRecordDTO logRecordDTO) {
        try {
            LogRecord createdRecord = logRecordService.createLogRecord(logRecordDTO);
            return ResponseEntity.ok(new ApiResponse<>("Log record created successfully", HttpStatus.OK.value(), createdRecord.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error creating log record: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateEndTime(@PathVariable String id) {
        try {
            LogRecord updatedRecord = logRecordService.updateEndTime(id);
            return ResponseEntity.ok(new ApiResponse<>("End time updated successfully", HttpStatus.OK.value(), updatedRecord.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Error updating end time: " + e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error updating end time: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LogRecordDTO>>> getAllRecords() {
        try {
            List<LogRecordDTO> records = logRecordService.getAllRecords();
            return ResponseEntity.ok(new ApiResponse<>("All records retrieved successfully", HttpStatus.OK.value(), records));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error retrieving records: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
        }
    }

    @GetMapping("/download-video")
    public ResponseEntity<Resource> downloadVideo(@RequestParam("recordType")RecordType recordType) {
        try {
            if(RecordType.VIDEO.equals(recordType)){
                return logRecordService.downloadRecord();
            }else {
                return logRecordService.downloadImage();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
