package com.mikolajj04.expense_tracker.dto;
import lombok.Data;
@Data
public class UserCreateRequest {
    private String email;
    private String username;
    private String password;
}