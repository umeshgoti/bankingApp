package com.bank.atm.service.impl;

import com.bank.atm.DTO.AtmDTO;
import com.bank.atm.Exception.ResourceNotFoundException;
import com.bank.atm.entity.Atm;
import com.bank.atm.repository.AtmRepository;
import com.bank.atm.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AtmServiceImpl implements AtmService {
    @Autowired
    private AtmRepository atmRepository;

    @Override
    public Atm createAtm(AtmDTO atmDTO) {
        Boolean existByLocation = atmRepository.existsByLocationName(atmDTO.getLocationName());
        if(existByLocation){
            throw new RuntimeException("Atm already exists with location: " + atmDTO.getLocationName());
        }
        Atm atm = AtmDTO.toEntity(atmDTO);
        return atmRepository.save(atm);
    }

    @Override
    public Atm updateAtm(String atmId, AtmDTO atmDTO) {
        Atm atm = AtmDTO.toEntity(atmDTO);
        Optional<Atm> existingAtm = atmRepository.findById(atmId);
        if (existingAtm.isPresent()) {
            Atm atmToUpdate = existingAtm.get();
            atmToUpdate.setLocationName(atm.getLocationName());
            atmToUpdate.setWelcomeMessage(atm.getWelcomeMessage());
            atmToUpdate.setActive(atm.isActive());
            atmToUpdate.setAmount(atm.getAmount());
            return atmRepository.save(atmToUpdate);
        } else {
            throw new RuntimeException("Atm not found with id: " + atmId);
        }
    }

    @Override
    public void deleteAtm(String atmId) {
        Optional<Atm> existingAtm = atmRepository.findById(atmId);
        if (existingAtm.isPresent()) {
            atmRepository.deleteById(atmId);
        } else {
            throw new RuntimeException("Atm not found with id: " + atmId);
        }
    }

    @Override
    public AtmDTO getAtmById(String atmId) {
        Atm atm = atmRepository.findById(atmId)
                .orElseThrow(() -> new ResourceNotFoundException("Atm with id: " + atmId));
        AtmDTO atmDTO = AtmDTO.fromEntity(atm);
        return atmDTO;
    }

    @Override
    public List<AtmDTO> getAllAtms() {
        List<Atm> all = atmRepository.findAll();
        List<AtmDTO> atmDTOS= new ArrayList<>();
        for(Atm atm : all){
            AtmDTO dto = AtmDTO.fromEntity(atm);
            atmDTOS.add(dto);
        }
        return atmDTOS;
    }
}
