package com.mikolajj04.expense_tracker.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@Column(nullable = false, unique = true)
    private String name;

@Column
    private String description;

@ManyToOne
    @JoinColumn(name =  "user_id")
    private User user;

}
