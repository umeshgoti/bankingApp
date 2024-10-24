package com.bank.atm.DTO;

import com.bank.atm.enumaration.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequestDTO {

    private String firstName;
    private String lastName;
    private String mobileNo;
    private String pin;
    private Roles role;
    private Double balance;

}
