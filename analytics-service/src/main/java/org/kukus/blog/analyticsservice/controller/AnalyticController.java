package org.kukus.blog.analyticsservice.controller;

import org.kukus.blog.analyticsservice.service.AnalyticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/analytics")
public class AnalyticController {

    private final AnalyticService analyticsService;

    @Autowired
    public AnalyticController(AnalyticService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/popularity/{userId}")
    public ResponseEntity<Double> getPopularity(@PathVariable String userId) {
        double popularity = analyticsService.getPopularity(userId);
        return ResponseEntity.ok(popularity);
    }

    @PostMapping("/popularity/batch")
    public ResponseEntity<Map<String, Double>> calculateBatch(@RequestBody Set<String> userIds) {
        Map<String, Double> result = analyticsService.calculateBatchPopularity(userIds);
        return ResponseEntity.ok(result);
    }
}
