package org.kukus.blog.feedservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private String id; // Unique identifier for the user
    private String username; // User's username
    private String email; // User's email
    private LocalDateTime createdAt; // Date and time when the user was created
    private List<String> followers; // List of user IDs who follow this user
}
