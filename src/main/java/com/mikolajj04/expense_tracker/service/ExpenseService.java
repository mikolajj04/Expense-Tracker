package com.mikolajj04.expense_tracker.service;
import com.mikolajj04.expense_tracker.model.Category;
import com.mikolajj04.expense_tracker.model.Expense;
import com.mikolajj04.expense_tracker.model.User;
import com.mikolajj04.expense_tracker.repository.ExpenseRepository;
import com.mikolajj04.expense_tracker.repository.UserRepository;
import com.mikolajj04.expense_tracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BudgetService budgetService;


    public Expense createExpense(Expense expense, Long userId, Long categoryId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));


        Category category = categoryRepository.findById(categoryId)
                .orElseThrow( () -> new RuntimeException("Category not found with id: " + categoryId));


        if (expense.getDate() == null) {
            expense.setDate(LocalDateTime.now());
        }


        budgetService.checkBudgetLimit(userId, categoryId, expense.getAmount(), expense.getDate());


        expense.setUser(user);
        expense.setCategory(category);

        return expenseRepository.save(expense);
    }
    public List<Expense> getExpensesByUserId(Long userId) {
        return expenseRepository.findByUserId(userId);
    }


    public void deleteExpense(Long id) {
        if(!expenseRepository.existsById(id)) {
            throw new RuntimeException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);}

}