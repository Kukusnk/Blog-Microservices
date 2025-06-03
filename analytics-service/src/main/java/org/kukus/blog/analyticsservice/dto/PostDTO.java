package org.kukus.blog.analyticsservice.dto;

import lombok.Data;
import org.kukus.blog.analyticsservice.model.Comment;

import java.util.List;

@Data
public class PostDTO {
    private String id;
    private String authorId;
    private int likes;
    private List<Comment> comments;
}
