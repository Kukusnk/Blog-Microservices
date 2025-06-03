package org.kukus.blog.feedservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    private String id; // Unique identifier for the post
    private String title; // Title of the post
    private String content; // Content of the post
    private String authorId; // ID of the user who created the post
    private List<String> photoUrls; // URLs of photos attached to the post
    private int likes; // Number of likes on the post
    private List<String> likedBy; // List of user IDs who liked the post
    private List<CommentDTO> comments; // List of comments on the post
    private LocalDateTime createdAt; // Date and time when the post was created
}
