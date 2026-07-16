package com.bhargava.expense_tracker.service;

import com.bhargava.expense_tracker.dto.IncomeRequest;
import com.bhargava.expense_tracker.dto.IncomeResponse;
import com.bhargava.expense_tracker.entity.Income;
import com.bhargava.expense_tracker.entity.User;
import com.bhargava.expense_tracker.repository.IncomeRepository;
import com.bhargava.expense_tracker.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;

    public IncomeService(IncomeRepository incomeRepository,
                         UserRepository userRepository) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
    }

    private User getLoggedInUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Add Income
    public IncomeResponse addIncome(IncomeRequest request) {

        User user = getLoggedInUser();

        Income income = Income.builder()
                .source(request.getSource())
                .amount(request.getAmount())
                .date(request.getDate())
                .description(request.getDescription())
                .user(user)
                .build();

        income = incomeRepository.save(income);

        return mapToResponse(income);
    }

    // Get All Income
    public List<IncomeResponse> getAllIncome() {

        User user = getLoggedInUser();

        return incomeRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get Income By Id
    public IncomeResponse getIncomeById(Long id) {

        User user = getLoggedInUser();

        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found"));

        if (!income.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to view this income");
        }

        return mapToResponse(income);
    }

    // Update Income
    public IncomeResponse updateIncome(Long id, IncomeRequest request) {

        User user = getLoggedInUser();

        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found"));

        if (!income.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this income");
        }

        income.setSource(request.getSource());
        income.setAmount(request.getAmount());
        income.setDate(request.getDate());
        income.setDescription(request.getDescription());

        income = incomeRepository.save(income);

        return mapToResponse(income);
    }

    // Delete Income
    public void deleteIncome(Long id) {

        User user = getLoggedInUser();

        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found"));

        if (!income.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this income");
        }

        incomeRepository.delete(income);
    }

    private IncomeResponse mapToResponse(Income income) {

        return IncomeResponse.builder()
                .id(income.getId())
                .source(income.getSource())
                .amount(income.getAmount())
                .date(income.getDate())
                .description(income.getDescription())
                .build();
    }
}