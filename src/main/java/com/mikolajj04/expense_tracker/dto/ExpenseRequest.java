package com.mikolajj04.expense_tracker.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class ExpenseRequest {
    private BigDecimal amount;
    private String description;
    private LocalDateTime date;

}
