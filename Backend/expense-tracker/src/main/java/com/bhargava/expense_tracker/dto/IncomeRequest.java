package com.bhargava.expense_tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IncomeRequest {

    @NotBlank(message = "Source is required")
    private String source;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private String description;
}