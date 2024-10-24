package com.bank.atm.DTO;

import com.bank.atm.entity.Transaction;
import com.bank.atm.enumaration.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionDTO {
    private String id;
    private String atmId;
    private String atmLocation;
    private String customerId;
    private String customerName;
    private LocalDateTime time;
    private TransactionType transactionType;
    private String status;
    private double transactionAmount;

    public Transaction createEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.getId());
        transaction.setTime(transactionDTO.getTime());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setStatus(transactionDTO.getStatus());
        transaction.setTransactionAmount(transactionDTO.getTransactionAmount());
        return transaction;
    }

    public static TransactionDTO fromEntity(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAtmId(transaction.getAtm().getId());
        dto.setAtmLocation(transaction.getAtm().getLocationName());
        dto.setCustomerId(transaction.getCustomer().getId());
        dto.setCustomerName(transaction.getCustomer().getFirstname()+" "+transaction.getCustomer().getLastname());
        dto.setTime(transaction.getTime());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setTransactionAmount(transaction.getTransactionAmount());
        return dto;
    }
}