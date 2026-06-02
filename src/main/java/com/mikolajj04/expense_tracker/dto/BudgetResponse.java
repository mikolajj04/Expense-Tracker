package com.mikolajj04.expense_tracker.dto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class BudgetResponse {
    private Long id;
    private BigDecimal limitAmount;
    private Integer month;
    private Integer year;
    private String categoryName;
}