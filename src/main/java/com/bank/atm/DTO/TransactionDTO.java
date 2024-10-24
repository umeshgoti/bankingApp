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
    private String customerId;
    private LocalDateTime time;
    private TransactionType transactionType;
    private String status;

    public Transaction createEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.getId());
        transaction.setTime(transactionDTO.getTime());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setStatus(transactionDTO.getStatus());
        return transaction;
    }
}