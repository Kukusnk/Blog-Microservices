package org.kukus.blog.postservice.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "posts")
@CompoundIndex(def = "{'createdAt': -1}", name = "createdAt_index")
@Data
public class Post {

    @Id
    private String id;
    private String title;
    private String content;
    private String authorId; // ID of the user who created the post
    private List<String> photoUrls; // URLs to photos associated with the post
    private int likes; // Number of likes
    private List<String> likedBy; // List of user IDs who liked the post
    private LocalDateTime createdAt;
    private List<Comment> comments;

    // Constructor
    public Post() {
        this.photoUrls = new ArrayList<>();
        this.likes = 0;
        this.likedBy = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }
}
