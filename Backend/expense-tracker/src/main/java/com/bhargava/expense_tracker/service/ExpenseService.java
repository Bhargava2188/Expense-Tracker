package com.bhargava.expense_tracker.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bhargava.expense_tracker.dto.ExpenseRequest;
import com.bhargava.expense_tracker.dto.ExpenseResponse;
import com.bhargava.expense_tracker.entity.Expense;
import com.bhargava.expense_tracker.entity.User;
import com.bhargava.expense_tracker.repository.ExpenseRepository;
import com.bhargava.expense_tracker.repository.UserRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    private User getLoggedInUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public ExpenseResponse addExpense(ExpenseRequest request) {

        User user = getLoggedInUser();

        Expense expense = Expense.builder()
                .title(request.getTitle())
                .amount(request.getAmount())
                .category(request.getCategory())
                .date(request.getDate())
                .description(request.getDescription())
                .user(user)
                .build();

        expense = expenseRepository.save(expense);

        return mapToResponse(expense);
    }

    public List<ExpenseResponse> getAllExpenses() {

        User user = getLoggedInUser();

        return expenseRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ExpenseResponse> getExpensesByCategory(String category) {

    User user = getLoggedInUser();

    return expenseRepository.findByUserAndCategory(user, category)
            .stream()
            .map(this::mapToResponse)
            .toList();
}

    public ExpenseResponse getExpenseById(Long id) {

    User user = getLoggedInUser();

    Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found"));

    if (!expense.getUser().getId().equals(user.getId())) {
        throw new RuntimeException("You are not authorized to view this expense");
    }

    return mapToResponse(expense);
}

    public void deleteExpense(Long id) {

    User user = getLoggedInUser();

    Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found"));

    if (!expense.getUser().getId().equals(user.getId())) {
        throw new RuntimeException("You are not authorized to delete this expense");
    }

    expenseRepository.delete(expense);
}

   

    public List<ExpenseResponse> getMonthlyExpenses(int month, int year) {

    User user = getLoggedInUser();

    YearMonth yearMonth = YearMonth.of(year, month);

    LocalDate startDate = yearMonth.atDay(1);
    LocalDate endDate = yearMonth.atEndOfMonth();

    return expenseRepository
            .findByUserAndDateBetween(user, startDate, endDate)
            .stream()
            .map(this::mapToResponse)
            .toList();
}

    private ExpenseResponse mapToResponse(Expense expense) {

        return ExpenseResponse.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .date(expense.getDate())
                .description(expense.getDescription())
                .build();
    }

    public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {

    User user = getLoggedInUser();

    Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found"));

    // Ensure the logged-in user owns this expense
    if (!expense.getUser().getId().equals(user.getId())) {
        throw new RuntimeException("You are not authorized to update this expense");
    }

    expense.setTitle(request.getTitle());
    expense.setAmount(request.getAmount());
    expense.setCategory(request.getCategory());
    expense.setDate(request.getDate());
    expense.setDescription(request.getDescription());

    expense = expenseRepository.save(expense);

    return mapToResponse(expense);
}
}