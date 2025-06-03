package org.kukus.blog.feedservice.controller;

import org.kukus.blog.feedservice.model.FeedItem;
import org.kukus.blog.feedservice.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public ResponseEntity<List<FeedItem>> getFeed() {
        List<FeedItem> feed = feedService.getFeedRecent();
        return ResponseEntity.ok(feed);
    }

}
