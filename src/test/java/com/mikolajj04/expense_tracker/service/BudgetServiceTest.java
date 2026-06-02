package com.mikolajj04.expense_tracker.service;

import com.mikolajj04.expense_tracker.model.Budget;
import com.mikolajj04.expense_tracker.model.Expense;
import com.mikolajj04.expense_tracker.repository.BudgetRepository;
import com.mikolajj04.expense_tracker.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private BudgetService budgetService;

    @Test
    void checkBudgetLimit_ShouldPass_WhenWithinBudget() {

        Long userId = 1L;
        Long categoryId = 2L;
        BigDecimal newExpenseAmount = new BigDecimal("200.00");
        LocalDateTime date = LocalDateTime.now();


        Budget mockBudget = new Budget();
        mockBudget.setLimitAmount(new BigDecimal("1000.00"));


        Expense pastExpense = new Expense();
        pastExpense.setAmount(new BigDecimal("500.00"));
        pastExpense.setDate(date);


        when(budgetRepository.findByUserIdAndCategoryIdAndMonthAndYear(
                userId, categoryId, date.getMonthValue(), date.getYear()))
                .thenReturn(Optional.of(mockBudget));


        when(expenseRepository.findByUserIdAndCategoryId(userId, categoryId))
                .thenReturn(List.of(pastExpense));


        assertDoesNotThrow(() ->
                budgetService.checkBudgetLimit(userId, categoryId, newExpenseAmount, date)
        );
    }

    @Test
    void checkBudgetLimit_ShouldThrowException_WhenBudgetExceeded() {

        Long userId = 1L;
        Long categoryId = 2L;
        BigDecimal newExpenseAmount = new BigDecimal("600.00");
        LocalDateTime date = LocalDateTime.now();

        Budget mockBudget = new Budget();
        mockBudget.setLimitAmount(new BigDecimal("1000.00"));

        Expense pastExpense = new Expense();
        pastExpense.setAmount(new BigDecimal("500.00"));
        pastExpense.setDate(date);

        when(budgetRepository.findByUserIdAndCategoryIdAndMonthAndYear(
                userId, categoryId, date.getMonthValue(), date.getYear()))
                .thenReturn(Optional.of(mockBudget));

        when(expenseRepository.findByUserIdAndCategoryId(userId, categoryId))
                .thenReturn(List.of(pastExpense));


        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                budgetService.checkBudgetLimit(userId, categoryId, newExpenseAmount, date)
        );


        assertTrue(exception.getMessage().contains("Budget limit exceeded"));
    }
}

