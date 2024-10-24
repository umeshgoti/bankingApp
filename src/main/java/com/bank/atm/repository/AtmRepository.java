package com.bank.atm.repository;

import com.bank.atm.entity.Atm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmRepository extends JpaRepository<Atm, String> {
    Boolean existsByLocationName(String locationName);
}
