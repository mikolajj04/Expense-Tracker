package com.mikolajj04.expense_tracker.service;
import com.mikolajj04.expense_tracker.model.Category;
import com.mikolajj04.expense_tracker.model.User;
import com.mikolajj04.expense_tracker.repository.CategoryRepository;
import com.mikolajj04.expense_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Category createCategory(Category category, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        if(categoryRepository.existsByUserIdAndName(userId, category.getName())) {
            throw new IllegalArgumentException("Category with name '" + category.getName() + "' already exists for this user.");
        }
        category.setUser(user);
        return categoryRepository.save(category);

    }
    public List<Category> getCategoriesByUserId(Long id){
        return categoryRepository.findByUserId(id);
    }
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);}
    }


