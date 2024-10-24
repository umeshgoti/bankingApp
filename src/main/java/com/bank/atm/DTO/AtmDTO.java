package com.bank.atm.DTO;

import com.bank.atm.entity.Atm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AtmDTO {
    private String locationName;
    private String welcomeMessage;
    private boolean isActive;
    private double amount;

    public static AtmDTO fromEntity(Atm atm) {
        AtmDTO dto = new AtmDTO();
        dto.setLocationName(atm.getLocationName());
        dto.setWelcomeMessage(atm.getWelcomeMessage());
        dto.setActive(atm.isActive());
        dto.setAmount(atm.getAmount());
        return dto;
    }

    public static Atm toEntity(AtmDTO dto) {
        Atm atm = new Atm();
        atm.setLocationName(dto.getLocationName());
        atm.setWelcomeMessage(dto.getWelcomeMessage());
        atm.setActive(dto.isActive());
        atm.setAmount(dto.getAmount());
        return atm;
    }
}