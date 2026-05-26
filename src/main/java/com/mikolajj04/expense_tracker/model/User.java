package com.mikolajj04.expense_tracker.model;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.dialect.function.DB2SubstringFunction;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false)
    private String username;

    @Column (nullable = false)
    private String password;

    @Column (name =  "created_at")
    private LocalDateTime createdAt;
}
