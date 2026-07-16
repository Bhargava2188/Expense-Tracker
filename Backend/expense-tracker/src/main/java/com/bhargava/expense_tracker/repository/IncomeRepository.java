package com.bhargava.expense_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bhargava.expense_tracker.entity.Income;
import com.bhargava.expense_tracker.entity.User;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUser(User user);

}