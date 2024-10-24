package com.bank.atm.entity;

import com.bank.atm.enumaration.Roles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@Table(name = "customer")
public class User extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "mobileNo", nullable = false, unique = true)
    private String mobileNo;

    @Column(name = "pin", nullable = false)
    private String pin;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Roles role;

    @Column(name = "balance", nullable = false)
    private Long balance;

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
