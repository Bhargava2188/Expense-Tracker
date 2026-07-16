package com.bhargava.expense_tracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhargava.expense_tracker.dto.IncomeRequest;
import com.bhargava.expense_tracker.dto.IncomeResponse;
import com.bhargava.expense_tracker.service.IncomeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/income")
@CrossOrigin(origins = "*")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    // Add Income
    @PostMapping
    public ResponseEntity<IncomeResponse> addIncome(
            @Valid @RequestBody IncomeRequest request) {

        return new ResponseEntity<>(
                incomeService.addIncome(request),
                HttpStatus.CREATED
        );
    }

    // Get All Income
    @GetMapping
    public ResponseEntity<List<IncomeResponse>> getAllIncome() {

        return ResponseEntity.ok(incomeService.getAllIncome());
    }

    // Get Income By Id
    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponse> getIncomeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(incomeService.getIncomeById(id));
    }

    // Update Income
    @PutMapping("/{id}")
    public ResponseEntity<IncomeResponse> updateIncome(
            @PathVariable Long id,
            @Valid @RequestBody IncomeRequest request) {

        return ResponseEntity.ok(
                incomeService.updateIncome(id, request)
        );
    }

    // Delete Income
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIncome(
            @PathVariable Long id) {

        incomeService.deleteIncome(id);

        return ResponseEntity.ok("Income deleted successfully");
    }
}