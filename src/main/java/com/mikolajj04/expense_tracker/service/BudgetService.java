package com.mikolajj04.expense_tracker.service;

import com.mikolajj04.expense_tracker.model.Budget;
import com.mikolajj04.expense_tracker.model.Expense;
import com.mikolajj04.expense_tracker.repository.BudgetRepository;
import com.mikolajj04.expense_tracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;

    public Budget createBudget(Budget budget) {
        Long userId = budget.getUser().getId();
        Long categoryId = budget.getCategory().getId();


        if (budgetRepository.findByUserIdAndCategoryIdAndMonthAndYear(
                userId, categoryId, budget.getMonth(), budget.getYear()).isPresent()) {
            throw new RuntimeException("Budget for this category and month already exists.");
        }

        return budgetRepository.save(budget);
    }

    public void checkBudgetLimit(Long userId, Long categoryId, BigDecimal newExpenseAmount, LocalDateTime expenseDate) {
        int month = expenseDate.getMonthValue();
        int year = expenseDate.getYear();

        budgetRepository.findByUserIdAndCategoryIdAndMonthAndYear(userId, categoryId, month, year)
                .ifPresent(budget -> {


                    List<Expense> expenses = expenseRepository.findByUserIdAndCategoryId(userId, categoryId);


                    BigDecimal currentSpent = expenses.stream()
                            .filter(e -> e.getDate().getMonthValue() == month && e.getDate().getYear() == year)
                            .map(Expense::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);


                    BigDecimal totalAfterNewExpense = currentSpent.add(newExpenseAmount);


                    if (totalAfterNewExpense.compareTo(budget.getLimitAmount()) > 0) {
                        BigDecimal remaining = budget.getLimitAmount().subtract(currentSpent);
                        throw new RuntimeException("Budget limit exceeded. Remaining budget: " + remaining);
                    }
                });
    }
}
