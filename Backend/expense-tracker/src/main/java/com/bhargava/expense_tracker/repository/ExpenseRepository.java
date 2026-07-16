package com.bhargava.expense_tracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bhargava.expense_tracker.entity.Expense;
import com.bhargava.expense_tracker.entity.User;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);
    List<Expense> findByUserAndCategory(User user, String category);

    List<Expense> findByUserAndDateBetween(
        User user,
        LocalDate start,
        LocalDate end
);


List<Expense> findByUserAndTitleContainingIgnoreCase(
        User user,
        String title
);




}