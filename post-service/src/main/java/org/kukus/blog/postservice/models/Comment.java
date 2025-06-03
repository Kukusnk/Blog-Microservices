package org.kukus.blog.postservice.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Comment {
    @Id
    private String id = UUID.randomUUID().toString(); // Generate UUID for id
    private String content;
    private String authorId; // ID of the user who wrote the comment
    private LocalDateTime createdAt;

    // Constructor
    public Comment() {
        this.createdAt = LocalDateTime.now();
    }

    public Comment(String content, String authorId) {
        this.id = UUID.randomUUID().toString(); // Generate UUID on creation
        this.content = content;
        this.authorId = authorId;
        this.createdAt = LocalDateTime.now();
    }
}