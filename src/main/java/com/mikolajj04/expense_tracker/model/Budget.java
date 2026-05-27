package com.mikolajj04.expense_tracker.model;



import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "budgets")
@Data
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal limitAmount;

    @Column (nullable = false)
    Integer month;

    @Column (nullable = false)
    Integer year;

    @ManyToOne
    @JoinColumn(name =  "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name =  "category_id")
    private Category category;
}
