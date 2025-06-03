package org.kukus.blog.feedservice.service;

import org.kukus.blog.feedservice.dto.CommentDTO;
import org.kukus.blog.feedservice.dto.PostDTO;
import org.kukus.blog.feedservice.dto.UserDTO;
import org.kukus.blog.feedservice.model.FeedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private static final Logger logger = LoggerFactory.getLogger(FeedService.class);

    private final RestTemplate restTemplate;

    @Autowired
    public FeedService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<FeedItem> getFeedRecent() {
        PostDTO[] posts = restTemplate.getForObject(
                "http://gateway-service:8081/post/recent",
                PostDTO[].class
        );

        if (posts == null || posts.length == 0) {
            logger.warn("Posts not found");
            return new ArrayList<>();
        }

        Set<String> authorIds = Arrays.stream(posts)
                .map(PostDTO::getAuthorId)
                .collect(Collectors.toSet());

        Map<String, UserDTO> users = getUsersByIds(authorIds);

        Map<String, Double> popularity = getPopularityByIds(authorIds);

        List<FeedItem> feedItems = new ArrayList<>();

        for (PostDTO post : posts) {
            FeedItem feedItem = new FeedItem();

            feedItem.setAuthorId(post.getAuthorId());
            feedItem.setPostId(post.getId());
            feedItem.setPostTitle(post.getTitle());
            feedItem.setPostContent(post.getContent());
            feedItem.setPhotoUrls(post.getPhotoUrls());
            feedItem.setCreatedAt(post.getCreatedAt());
            feedItem.setLikes(post.getLikes());
            feedItem.setLikedBy(post.getLikedBy());
            feedItem.setComments(post.getComments());

            UserDTO user = users.get(post.getAuthorId());
            feedItem.setAuthorUsername(user!=null?user.getUsername():"Unknown");

            feedItem.setPopularityScore(popularity.getOrDefault(post.getAuthorId(), 0.0));

            feedItems.add(feedItem);
        }


        return feedItems;
    }

    private Map<String, UserDTO> getUsersByIds(Set<String> authorIds) {
        try {
            String url = "http://gateway-service:8081/users/batch/ids";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Set<String>> request = new HttpEntity<>(authorIds, headers);

            ResponseEntity<Map<String, UserDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, UserDTO>>() {}
            );

            return response.getBody() != null ? response.getBody() : new HashMap<>();
        } catch (Exception e) {
            logger.error("Failed to fetch users by IDs: {}", e.getMessage());
            return new HashMap<>();
        }
    }

    private Map<String, Double> getPopularityByIds(Set<String> authorIds) {
        try {
            String url = "http://gateway-service:8081/analytics/popularity/batch";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Set<String>> request = new HttpEntity<>(authorIds, headers);

            ResponseEntity<Map<String, Double>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Double>>() {}
            );

            return response.getBody() != null ? response.getBody() : new HashMap<>();
        } catch (Exception e) {
            logger.error("Failed to fetch popularity by IDs: {}", e.getMessage());
            return new HashMap<>();
        }
    }
}
