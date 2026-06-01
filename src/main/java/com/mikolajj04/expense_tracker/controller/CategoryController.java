package com.mikolajj04.expense_tracker.controller;

import com.mikolajj04.expense_tracker.dto.CategoryRequest;
import com.mikolajj04.expense_tracker.dto.CategoryResponse;
import com.mikolajj04.expense_tracker.model.Category;
import com.mikolajj04.expense_tracker.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{userId}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @PathVariable Long userId,
            @RequestBody CategoryRequest request) {

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category savedCategory = categoryService.createCategory(category, userId);
        return new ResponseEntity<>(mapToResponse(savedCategory), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(@PathVariable Long userId) {
        List<CategoryResponse> responses = categoryService.getCategoriesByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }
}