package org.kukus.blog.feedservice.model;

import lombok.Data;
import org.kukus.blog.feedservice.dto.CommentDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FeedItem {
    private String authorId;
    private String authorUsername; // User's username

    private double popularityScore;

    private String postId;
    private String postTitle; // Title of the post
    private String postContent; // Content of the post
    private List<String> photoUrls; // URLs of photos attached to the post
    private int likes; // Number of likes on the post
    private List<String> likedBy; // List of user IDs who liked the post
    private List<CommentDTO> comments; // List of comments on the post
    private LocalDateTime createdAt;
}
