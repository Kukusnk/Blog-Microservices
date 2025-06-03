package org.kukus.blog.analyticsservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopularityData {
    private LocalDateTime createdAt;
    private int followersCount;
    private int totalLikes;
    private int totalComments;
    private int postsCount;
}
