package com.bank.atm.repository;

import com.bank.atm.entity.Atm;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtmRepository extends JpaRepository<Atm, String> {
    Boolean existsByLocationName(String locationName);
    List<Atm> findAll(Sort sort);

}
