package com.mikolajj04.expense_tracker.dto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class BudgetRequest {
    private BigDecimal limitAmount;
    private Integer month;
    private Integer year;
}