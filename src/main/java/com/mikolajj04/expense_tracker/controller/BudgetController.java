package com.mikolajj04.expense_tracker.controller;
import com.mikolajj04.expense_tracker.dto.BudgetRequest;
import com.mikolajj04.expense_tracker.dto.BudgetResponse;
import com.mikolajj04.expense_tracker.model.Budget;
import com.mikolajj04.expense_tracker.model.Category;
import com.mikolajj04.expense_tracker.model.User;
import com.mikolajj04.expense_tracker.repository.CategoryRepository;
import com.mikolajj04.expense_tracker.repository.UserRepository;
import com.mikolajj04.expense_tracker.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/categories/{categoryId}/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(
            @PathVariable Long userId,
            @PathVariable Long categoryId,
            @RequestBody BudgetRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Budget budget = new Budget();
        budget.setLimitAmount(request.getLimitAmount());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());
        budget.setUser(user);
        budget.setCategory(category);

        Budget savedBudget = budgetService.createBudget(budget);

        return new ResponseEntity<>(mapToResponse(savedBudget), HttpStatus.CREATED);
    }

    private BudgetResponse mapToResponse(Budget budget) {
        BudgetResponse response = new BudgetResponse();
        response.setId(budget.getId());
        response.setLimitAmount(budget.getLimitAmount());
        response.setMonth(budget.getMonth());
        response.setYear(budget.getYear());
        response.setCategoryName(budget.getCategory().getName());
        return response;
    }
}