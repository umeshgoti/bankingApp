package com.bank.atm.entity;

import com.bank.atm.enumaration.RecordType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "record")
public class LogRecord extends BaseEntity{
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_type", nullable = false)
    private RecordType recordType;

    @Column(name = "start_time", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime endTime;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.createdBy = getCurrentUsername();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedDate = LocalDateTime.now();
        this.updatedBy = getCurrentUsername();
    }
}
