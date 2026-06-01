package com.mikolajj04.expense_tracker.dto;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private LocalDateTime createdAt;
}
