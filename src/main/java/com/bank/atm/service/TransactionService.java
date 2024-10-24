package com.bank.atm.service;

import com.bank.atm.DTO.TransactionDTO;
import com.bank.atm.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(TransactionDTO transaction);

    TransactionDTO getTransactionById(String transactionId);

    List<TransactionDTO> getAllTransactions();

    Double getAcountBalance(TransactionDTO transaction);
}
