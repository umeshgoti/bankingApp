package com.bank.atm.Controller;

import com.bank.atm.DTO.ApiResponse;
import com.bank.atm.DTO.TransactionDTO;
import com.bank.atm.Exception.ResourceNotFoundException;
import com.bank.atm.Exception.ThirdPartyExceptions;
import com.bank.atm.entity.Transaction;
import com.bank.atm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            Transaction transaction = transactionService.createTransaction(transactionDTO);
            Double accountBalance = transactionService.getAcountBalance(transactionDTO);
            return ResponseEntity.ok(new ApiResponse<>("Your Account Balance is : " + accountBalance, HttpStatus.OK.value(), transaction.getId()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
        } catch (ThirdPartyExceptions e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getTransactionById(@PathVariable String id) {
        try {
            TransactionDTO transactionDTO = transactionService.getTransactionById(id);
            return ResponseEntity.ok(new ApiResponse<>("Transaction retrieved successfully", HttpStatus.OK.value(), transactionDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllTransactions() {
        try {
            List<TransactionDTO> allTransactions = transactionService.getAllTransactions();
            return ResponseEntity.ok(new ApiResponse<>("All transactions retrieved successfully", HttpStatus.OK.value(), allTransactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }
}
