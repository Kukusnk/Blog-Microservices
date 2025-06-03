package org.kukus.blog.analyticsservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private String id;
    private String username;
    private LocalDateTime createdAt;
    private List<String> followers;
}

