package com.bhargava.expense_tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeResponse {

    private Long id;

    private String source;

    private BigDecimal amount;

    private LocalDate date;

    private String description;
}