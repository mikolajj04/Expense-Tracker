package com.mikolajj04.expense_tracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.mikolajj04.expense_tracker.model.Category;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
List<Category> findByUserId(Long userId);
boolean existsByUserIdAndName(Long userId, String name);
}
