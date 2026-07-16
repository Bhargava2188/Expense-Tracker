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

import com.bhargava.expense_tracker.dto.ExpenseRequest;
import com.bhargava.expense_tracker.dto.ExpenseResponse;
import com.bhargava.expense_tracker.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Add Expense
    @PostMapping
    public ResponseEntity<ExpenseResponse> addExpense(
            @Valid @RequestBody ExpenseRequest request) {

        ExpenseResponse response = expenseService.addExpense(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get All Expenses
    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {

        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    // Get Expense By Id
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(
            @PathVariable Long id) {

        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    // Delete Expense
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(
            @PathVariable Long id) {

        expenseService.deleteExpense(id);

        return ResponseEntity.ok("Expense deleted successfully");
    }

    @PutMapping("/{id}")
public ResponseEntity<ExpenseResponse> updateExpense(
        @PathVariable Long id,
        @Valid @RequestBody ExpenseRequest request) {

    return ResponseEntity.ok(expenseService.updateExpense(id, request));
}

@GetMapping("/category/{category}")
public ResponseEntity<List<ExpenseResponse>> getExpensesByCategory(
        @PathVariable String category) {

    return ResponseEntity.ok(
            expenseService.getExpensesByCategory(category)
    );
}

@GetMapping("/month/{month}/{year}")
public ResponseEntity<List<ExpenseResponse>> getMonthlyExpenses(
        @PathVariable int month,
        @PathVariable int year) {

    return ResponseEntity.ok(
            expenseService.getMonthlyExpenses(month, year)
    );
}
}