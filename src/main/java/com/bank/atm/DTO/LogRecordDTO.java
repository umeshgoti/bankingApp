package com.bank.atm.DTO;
import com.bank.atm.entity.LogRecord;
import com.bank.atm.enumaration.RecordType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRecordDTO {
    private String id;

    private RecordType recordType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;


    public static LogRecordDTO fromEntity(LogRecord logRecord) {
        if (logRecord == null) {
            return null;
        }
        return new LogRecordDTO(
                logRecord.getId(),
                logRecord.getRecordType(),
                logRecord.getStartTime(),
                logRecord.getEndTime()
        );
    }
    public static LogRecord toEntity(LogRecordDTO dto) {
        LogRecord logRecord = new LogRecord();
        logRecord.setRecordType(dto.getRecordType());
        logRecord.setStartTime(LocalDateTime.now());
        return logRecord;
    }
}
