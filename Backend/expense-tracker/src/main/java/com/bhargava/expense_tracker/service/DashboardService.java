package com.bhargava.expense_tracker.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bhargava.expense_tracker.dto.DashboardResponse;
import com.bhargava.expense_tracker.entity.Expense;
import com.bhargava.expense_tracker.entity.Income;
import com.bhargava.expense_tracker.entity.User;
import com.bhargava.expense_tracker.repository.ExpenseRepository;
import com.bhargava.expense_tracker.repository.IncomeRepository;
import com.bhargava.expense_tracker.repository.UserRepository;

@Service
public class DashboardService {

    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;

    public DashboardService(
            ExpenseRepository expenseRepository,
            IncomeRepository incomeRepository,
            UserRepository userRepository) {

        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
    }

    private User getLoggedInUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public DashboardResponse getDashboard() {

        User user = getLoggedInUser();

        List<Expense> expenses = expenseRepository.findByUser(user);
        List<Income> incomes = incomeRepository.findByUser(user);

        BigDecimal totalExpense = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalIncome = incomes.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = totalIncome.subtract(totalExpense);

        return DashboardResponse.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .balance(balance)
                .incomeCount((long) incomes.size())
                .expenseCount((long) expenses.size())
                .build();
    }
}