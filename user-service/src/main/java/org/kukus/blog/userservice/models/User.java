package org.kukus.blog.userservice.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Represents a user document in the "users" collection
@Document(collection = "users")
@Data
public class User {
    @Id
    private String id; // Unique identifier for the user
    private String username; // User's username
    @Indexed(unique = true)
    private String email; // User's email
    private String password;// Hashed password
    private LocalDateTime createdAt; // Added field for account creation date
    private List<String> followers; // List of user IDs who follow this user

    // Constructor
    public User() {
        this.createdAt = LocalDateTime.now(); // Set creation date on instantiation
        this.followers = new ArrayList<>();
    }
}
