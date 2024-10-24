package com.bank.atm.service.impl;

import com.bank.atm.DTO.TransactionDTO;
import com.bank.atm.Exception.ResourceNotFoundException;
import com.bank.atm.Exception.ThirdPartyExceptions;
import com.bank.atm.entity.Atm;
import com.bank.atm.entity.Transaction;
import com.bank.atm.entity.User;
import com.bank.atm.enumaration.TransactionType;
import com.bank.atm.repository.AtmRepository;
import com.bank.atm.repository.TransactionRepository;
import com.bank.atm.repository.UserRepository;
import com.bank.atm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AtmRepository atmRepository;

    @Autowired
    private UserRepository userRepository;

    public TransactionServiceImpl() {
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) {
        Atm atm = atmRepository.findById(transactionDTO.getAtmId()).orElseThrow(() -> new ResourceNotFoundException("ATM"));
        User user = userRepository.findById(transactionDTO.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("User"));
        Transaction transaction;
        if(TransactionType.WITHDRAW.equals(transactionDTO.getTransactionType())) {
            if (user.getBalance() < transactionDTO.getTransactionAmount()) {
                transaction = transactionDTO.createEntity(transactionDTO);
                transaction.setStatus("Failed");
                transactionRepository.save(transaction);
                throw new ThirdPartyExceptions(HttpStatus.BAD_REQUEST.value(), "Insufficient Balance!");
            }
            if (atm.getAmount() < transactionDTO.getTransactionAmount()) {
                transaction = transactionDTO.createEntity(transactionDTO);
                transaction.setStatus("Failed");
                transactionRepository.save(transaction);
                throw new ThirdPartyExceptions(HttpStatus.BAD_REQUEST.value(), "ATM is unable to Withdraw Cash at a Moment!");
            }
            atm.setAmount(atm.getAmount()-transactionDTO.getTransactionAmount());
            user.setBalance(user.getBalance()-transactionDTO.getTransactionAmount());
            transaction = transactionDTO.createEntity(transactionDTO);
            transaction.setStatus("Success");
        } else if (TransactionType.DEPOSIT.equals(transactionDTO.getTransactionType())) {
            atm.setAmount(atm.getAmount()+transactionDTO.getTransactionAmount());
            user.setBalance(user.getBalance()+transactionDTO.getTransactionAmount());
            transaction = transactionDTO.createEntity(transactionDTO);
            transaction.setStatus("Success");
        }else{
            transaction = transactionDTO.createEntity(transactionDTO);
        }
        transaction.setAtm(atm);
        transaction.setCustomer(user);
        userRepository.save(user);
        atmRepository.save(atm);
        return transactionRepository.save(transaction);
    }

    @Override
    public TransactionDTO getTransactionById(String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction with id: " + transactionId));
        TransactionDTO dto = TransactionDTO.fromEntity(transaction);
        return dto;
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDTO> dtos = new ArrayList<>();
        for(Transaction transaction:transactions){
            dtos.add(TransactionDTO.fromEntity(transaction));
        }
        return dtos;
    }

    @Override
    public Double getAcountBalance(TransactionDTO transaction) {
        User user = userRepository.findById(transaction.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("User"));
        return user.getBalance();
    }
}
