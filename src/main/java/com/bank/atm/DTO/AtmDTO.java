package com.bank.atm.DTO;

import com.bank.atm.entity.Atm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AtmDTO {
    private String id;
    private String locationName;
    private String welcomeMessage;
    private Boolean isActive;
    private double amount;

    public static AtmDTO fromEntity(Atm atm) {
        AtmDTO dto = new AtmDTO();
        dto.setId(atm.getId());
        dto.setLocationName(atm.getLocationName());
        dto.setWelcomeMessage(atm.getWelcomeMessage());
        dto.setIsActive(atm.isActive());
        dto.setAmount(atm.getAmount());
        return dto;
    }

    public static Atm toEntity(AtmDTO dto) {
        Atm atm = new Atm();
        atm.setLocationName(dto.getLocationName());
        atm.setWelcomeMessage(dto.getWelcomeMessage());
        atm.setActive(dto.getIsActive());
        atm.setAmount(dto.getAmount());
        return atm;
    }
}