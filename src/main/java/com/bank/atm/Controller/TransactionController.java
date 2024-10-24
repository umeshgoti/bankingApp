package com.bank.atm.Controller;

import com.bank.atm.DTO.ApiResponse;
import com.bank.atm.DTO.TransactionDTO;
import com.bank.atm.Exception.ThirdPartyExceptions;
import com.bank.atm.entity.Transaction;
import com.bank.atm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createTransaction(@RequestBody TransactionDTO transaction) {
        try {
            Transaction transaction1 = transactionService.createTransaction(transaction);
            Double acountBalance = transactionService.getAcountBalance(transaction);
            return ResponseEntity.ok(new ApiResponse<>("Your Acount Balance is : "+acountBalance, HttpStatus.OK.value(),transaction1.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getTransactionById(@PathVariable String id) {
        try {
            TransactionDTO transactionDTO = transactionService.getTransactionById(id);
            return ResponseEntity.ok(new ApiResponse<>("Transaction retrieved successfully", HttpStatus.OK.value(), transactionDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllTransactions() {
        try {
            List<TransactionDTO> allTransactions = transactionService.getAllTransactions();
            return ResponseEntity.ok(new ApiResponse<>("All transactions retrieved successfully", HttpStatus.OK.value(), allTransactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }
}
