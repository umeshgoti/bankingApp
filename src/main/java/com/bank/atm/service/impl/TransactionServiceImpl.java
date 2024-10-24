package com.bank.atm.service.impl;

import com.bank.atm.DTO.TransactionDTO;
import com.bank.atm.Exception.ResourceNotFoundException;
import com.bank.atm.entity.Atm;
import com.bank.atm.entity.Transaction;
import com.bank.atm.entity.User;
import com.bank.atm.repository.AtmRepository;
import com.bank.atm.repository.TransactionRepository;
import com.bank.atm.repository.UserRepository;
import com.bank.atm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AtmRepository AtmRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionDTO.createEntity(transactionDTO);
        Atm atm = AtmRepository.findById(transactionDTO.getAtmId()).orElseThrow(() -> new ResourceNotFoundException("ATM"));
        transaction.setAtm(atm);
        User user = userRepository.findById(Integer.valueOf(transactionDTO.getCustomerId())).orElseThrow(() -> new ResourceNotFoundException("User"));
        transaction.setCustomer(user);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(String transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction with id: " + transactionId));
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
