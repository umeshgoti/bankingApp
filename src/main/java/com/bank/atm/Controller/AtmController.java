package com.bank.atm.Controller;

import com.bank.atm.DTO.ApiResponse;
import com.bank.atm.DTO.AtmDTO;
import com.bank.atm.entity.Atm;
import com.bank.atm.service.AtmService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atm")
public class AtmController {

    @Autowired
    private AtmService atmService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createAtm(@RequestBody AtmDTO atmDTO, HttpServletRequest httpServletRequest) {
        try {
            Atm atm = atmService.createAtm(atmDTO);
            return ResponseEntity.ok(new ApiResponse<>("ATM added successfully.", HttpStatus.OK.value(), atm.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateAtm(@PathVariable String id, @RequestBody AtmDTO atmDTO) {
        try {
            Atm atm = atmService.updateAtm(id, atmDTO);
            return ResponseEntity.ok(new ApiResponse<>("ATM updated successfully", HttpStatus.OK.value(), atm.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAtm(@PathVariable String id) {
        try {
            atmService.deleteAtm(id);
            return ResponseEntity.ok(new ApiResponse<>("ATM deleted successfully", HttpStatus.OK.value(), null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getAtmById(@PathVariable String id) {
        try {
            AtmDTO atmDTO = atmService.getAtmById(id);
            return ResponseEntity.ok(new ApiResponse<>("ATM retrieved successfully", HttpStatus.OK.value(), atmDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllAtms() {
        try {
            List<AtmDTO> allAtms = atmService.getAllAtms();
            return ResponseEntity.ok(new ApiResponse<>("All ATMs retrieved successfully", HttpStatus.OK.value(), allAtms));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }
}
