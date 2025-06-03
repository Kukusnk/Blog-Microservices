package org.kukus.blog.postservice.dto;

import lombok.Data;
import org.kukus.blog.postservice.models.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EnrichedPostDTO {
    private String id; // Unique identifier for the post
    private String title; // Title of the post
    private String content; // Content of the post
    private String authorId; // ID of the user who created the post
    private String authorUsername; // Username of the post author
    private double popularityScore; // Popularity score of the author
    private List<String> photoUrls; // URLs of photos attached to the post
    private int likes; // Number of likes on the post
    private List<String> likedBy; // List of user IDs who liked the post
    private List<Comment> comments; // List of comments on the post
    private LocalDateTime createdAt; // Date and time when the post was created
}
