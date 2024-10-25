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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Atm atm = atmRepository.findById(transactionDTO.getAtmId()).orElseThrow(() -> new ResourceNotFoundException("ATM not found"));
        User user = userRepository.findById(transactionDTO.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Transaction transaction = transactionDTO.createEntity(transactionDTO);

        boolean isSuccessful = processTransaction(transactionDTO, atm, user, transaction);
        transaction.setAtm(atm);
        transaction.setCustomer(user);
        transaction.setStatus(isSuccessful ? "Success" : "Failed");

        return transactionRepository.save(transaction);
    }

    private boolean processTransaction(TransactionDTO transactionDTO, Atm atm, User user, Transaction transaction) {
        boolean isSuccessful = false;

        if (TransactionType.WITHDRAW.equals(transactionDTO.getTransactionType())) {
            if (user.getBalance() < transactionDTO.getTransactionAmount()) {
                transaction.setAtm(atm);
                transaction.setCustomer(user);
                transaction.setStatus("Failed");
                transactionRepository.save(transaction);
                throw new ThirdPartyExceptions(HttpStatus.BAD_REQUEST.value(), "Insufficient Balance!");
            }
            if (atm.getAmount() < transactionDTO.getTransactionAmount()) {
                transaction.setAtm(atm);
                transaction.setCustomer(user);
                transaction.setStatus("Failed");
                transactionRepository.save(transaction);
                throw new ThirdPartyExceptions(HttpStatus.BAD_REQUEST.value(), "ATM is unable to Withdraw Cash at a Moment!");
            }

            atm.setAmount(atm.getAmount() - transactionDTO.getTransactionAmount());
            user.setBalance(user.getBalance() - transactionDTO.getTransactionAmount());
            isSuccessful = true;

        } else if (TransactionType.DEPOSIT.equals(transactionDTO.getTransactionType())) {
            atm.setAmount(atm.getAmount() + transactionDTO.getTransactionAmount());
            user.setBalance(user.getBalance() + transactionDTO.getTransactionAmount());
            isSuccessful = true;
        }else{
            isSuccessful = true;
        }

        userRepository.save(user);
        atmRepository.save(atm);

        return isSuccessful;
    }

    @Override
    public Double getAcountBalance(TransactionDTO transaction) {
        User user = userRepository.findById(transaction.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getBalance();
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
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime timeThreshold = now.minus(24, ChronoUnit.HOURS);

        List<Transaction> transactions = transactionRepository.findTransactionsWithinLast24Hours(timeThreshold);
        return transactions.stream()
                .map(TransactionDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
