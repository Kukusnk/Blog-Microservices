package org.kukus.blog.userservice.dto;

import lombok.Data;

// DTO for creating a new user (includes password)
@Data
public class UserCreateDTO {
    private String username; // User's username
    private String email; // User's email
    private String password; // User's password
}