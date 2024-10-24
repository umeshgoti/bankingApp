package com.bank.atm.service;

import com.bank.atm.DTO.TransactionDTO;
import com.bank.atm.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(TransactionDTO transaction);

    Transaction getTransactionById(String transactionId);

    List<Transaction> getAllTransactions();
}
