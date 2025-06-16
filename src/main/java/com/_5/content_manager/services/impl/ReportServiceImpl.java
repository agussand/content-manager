package com._5.content_manager.services.impl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import com._5.content_manager.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Map> getPostsByUserReport() {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("status").is("published")),
                        group("author.userId")
                        .first("author.displayName").as("authorName")
                        .count().as("totalPosts")
                        .sum("stats.viewsCount").as("totalViews")
                        .sum("stats.likesCount").as("totalLikes"),
                sort(Sort.Direction.DESC, "totalPosts"),
                limit(10)
        );
        return mongoTemplate.aggregate(aggregation, "posts", Map.class).getMappedResults();
    }

    @Override
    public List<Map> getTopCommentedPostsReport() {
        return List.of();
    }

    @Override
    public List<Map> getPostsByTagAndDateReport(List<String> tags, LocalDateTime startDate, LocalDateTime endDate) {
        return List.of();
    }

    @Override
    public List<Map> getMonthlyPostsAverageReport() {
        return List.of();
    }

    @Override
    public List<Map> getCommentsAnalysisReport() {
        return List.of();
    }

    @Override
    public Map<String, Object> getDashboardStats() {
        return Map.of();
    }
}
