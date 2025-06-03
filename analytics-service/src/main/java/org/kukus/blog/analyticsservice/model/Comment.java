package org.kukus.blog.analyticsservice.model;

import lombok.Data;

@Data
public class Comment {
    private String id;
    private String content;
    private String authorId;
}
