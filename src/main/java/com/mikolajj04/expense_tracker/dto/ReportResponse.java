package com.mikolajj04.expense_tracker.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private String categoryName;
    private BigDecimal totalAmount;
}