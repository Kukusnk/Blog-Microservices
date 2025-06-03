package org.kukus.blog.postservice.dto;

import lombok.Data;
import org.kukus.blog.postservice.models.Comment;
import org.kukus.blog.postservice.models.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostDTO {
    private String id;
    private String title;
    private String content;
    private String authorId;
    private List<String> photoUrls;
    private int likes;
    private List<String> likedBy;
    private LocalDateTime createdAt;
    // Constructors
    private List<Comment> comments; // New field for comments

    // Constructors
    public PostDTO() {
        this.comments = new ArrayList<>(); // Initialize comments list
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorId = post.getAuthorId();
        this.photoUrls = post.getPhotoUrls();
        this.likes = post.getLikes();
        this.likedBy = post.getLikedBy();
        this.createdAt = post.getCreatedAt();
        this.comments = post.getComments();
    }
}
