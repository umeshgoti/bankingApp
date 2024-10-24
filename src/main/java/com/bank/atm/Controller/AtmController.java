package com.bank.atm.Controller;

import com.bank.atm.DTO.AtmDTO;
import com.bank.atm.entity.Atm;
import com.bank.atm.service.AtmService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atm")
public class AtmController {

    @Autowired
    private AtmService atmService;

    @PostMapping
    public ResponseEntity<String> createAtm(@RequestBody AtmDTO atmDTO, HttpServletRequest httpServletRequest) {
        try{
            Atm atm = atmService.createAtm(atmDTO);
            return ResponseEntity.ok(atm.getId());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atm> updateAtm(@PathVariable String id, @RequestBody AtmDTO atmDTO) {
        return ResponseEntity.ok(atmService.updateAtm(id, atmDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtm(@PathVariable String id) {
        atmService.deleteAtm(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atm> getAtmById(@PathVariable String id) {
        return ResponseEntity.ok(atmService.getAtmById(id));
    }

    @GetMapping
    public ResponseEntity<List<Atm>> getAllAtms() {
        return ResponseEntity.ok(atmService.getAllAtms());
    }
}
