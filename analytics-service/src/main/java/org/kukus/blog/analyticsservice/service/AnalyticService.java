package org.kukus.blog.analyticsservice.service;

import org.kukus.blog.analyticsservice.dto.PostDTO;
import org.kukus.blog.analyticsservice.dto.UserDTO;
import org.kukus.blog.analyticsservice.model.PopularityData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AnalyticService {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticService.class);

    private final RestTemplate restTemplate;

    @Autowired
    public AnalyticService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PopularityData gatherPopularityData(String userId) {
        UserDTO user = restTemplate.getForObject(
                "http://gateway-service:8081/users/" + userId,
                UserDTO.class
        );
        if (user == null) return new PopularityData();

        PostDTO[] posts = restTemplate.getForObject(
                "http://gateway-service:8081/post/author/" + userId,
                PostDTO[].class
        );

        int totalLikes = 0;
        int totalComments = 0;
        if (posts != null) {
            for (PostDTO post : posts) {
                totalLikes += post.getLikes();
                totalComments += post.getComments() != null ? post.getComments().size() : 0;
            }
        }

        return new PopularityData(
                user.getCreatedAt(),
                user.getFollowers() != null ? user.getFollowers().size() : 0,
                totalLikes,
                totalComments,
                posts != null ? posts.length : 0
        );
    }

    public double calculatePopularity(PopularityData data) {
        long daysSinceCreation = ChronoUnit.DAYS.between(data.getCreatedAt(), LocalDateTime.now());

        return daysSinceCreation
                + data.getFollowersCount()
                + data.getTotalLikes()
                + data.getTotalComments()
                + data.getPostsCount();
    }

    public double getPopularity(String userId) {
        try {
            PopularityData data = gatherPopularityData(userId);
            return calculatePopularity(data);
        } catch (Exception e) {
            logger.error("Error calculating popularity for userId: {}", userId, e);
            return 0.0;
        }
    }

    public Map<String, Double> calculateBatchPopularity(Set<String> userIds) {
        Map<String, Double> result = new HashMap<>();

        for (String userId : userIds) {
            try {
                result.put(userId, getPopularity(userId));
            } catch (Exception e) {
                result.put(userId, 0.0); // или null — в зависимости от политики
            }
        }

        return result;
    }
}
