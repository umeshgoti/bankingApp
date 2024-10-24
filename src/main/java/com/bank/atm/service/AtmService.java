package com.bank.atm.service;

import com.bank.atm.DTO.AtmDTO;
import com.bank.atm.entity.Atm;

import java.util.List;

public interface AtmService {
    Atm createAtm(AtmDTO atmDTO);

    Atm updateAtm(String atmId, AtmDTO atmDTO);

    void deleteAtm(String atmId);

    Atm getAtmById(String atmId);

    List<Atm> getAllAtms();
}
