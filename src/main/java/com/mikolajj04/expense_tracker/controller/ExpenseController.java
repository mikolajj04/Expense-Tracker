package com.mikolajj04.expense_tracker.controller;
import com.mikolajj04.expense_tracker.dto.ExpenseRequest;
import com.mikolajj04.expense_tracker.dto.ExpenseResponse;
import com.mikolajj04.expense_tracker.model.Expense;
import com.mikolajj04.expense_tracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{userId}")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;


    @PostMapping("/categories/{categoryId}/expenses")
    public ResponseEntity<ExpenseResponse> createExpense(
            @PathVariable Long userId,
            @PathVariable Long categoryId,
            @RequestBody ExpenseRequest request) {

        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setDate(request.getDate());

        Expense savedExpense = expenseService.createExpense(expense, userId, categoryId);
        return new ResponseEntity<>(mapToResponse(savedExpense), HttpStatus.CREATED);
    }


    @GetMapping("/expenses")
    public ResponseEntity<List<ExpenseResponse>> getUserExpenses(@PathVariable Long userId) {
        List<ExpenseResponse> responses = expenseService.getExpensesByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/expenses/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }

    private ExpenseResponse mapToResponse(Expense expense) {
        ExpenseResponse response = new ExpenseResponse();
        response.setId(expense.getId());
        response.setAmount(expense.getAmount());
        response.setDescription(expense.getDescription());
        response.setDate(expense.getDate());
        return response;
    }
}