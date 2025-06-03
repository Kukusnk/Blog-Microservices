package org.kukus.blog.authservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    private String username;

    @Indexed(unique = true)
    private String email;

    private String password; // будет храниться в зашифрованном виде

    private LocalDateTime createdAt = LocalDateTime.now();

    private List<String> followers = new ArrayList<>();
}
