package org.kukus.blog.feedservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private String id;
    private String content;
    private String authorId;
    private LocalDateTime createdAt;
}
