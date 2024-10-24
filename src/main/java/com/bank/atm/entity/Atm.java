package com.bank.atm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "atm")
public class Atm extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "location_name", nullable = false,unique = true)
    private String locationName;

    @Column(name = "welcome_message", nullable = true)
    private String welcomeMessage;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "amount", nullable = false)
    private Double amount;

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
