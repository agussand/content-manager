package com._5.content_manager.services.impl;


import com._5.content_manager.services.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public List<Map> getPostsByUserReport() {
        return List.of();
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
