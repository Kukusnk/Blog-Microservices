package org.kukus.blog.analyticsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopularityBatchResponse {
    private Map<String, Double> popularityMap;
}

