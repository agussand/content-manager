package com._5.content_manager.controllers;


import com._5.content_manager.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/posts-by-user")
    public ResponseEntity<List<Map>> getPostsByUserReport() {
        List<Map> report = reportService.getPostsByUserReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/top-commented-posts")
    public ResponseEntity<List<Map>> getTopCommentedPostsReport() {
        List<Map> report = reportService.getTopCommentedPostsReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/posts-by-tag-and-date")
    public ResponseEntity<List<Map>> getPostsByTagAndDateReport(
            @RequestParam List<String> tags,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<Map> report = reportService.getPostsByTagAndDateReport(tags, startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/monthly-average")
    public ResponseEntity<List<Map>> getMonthlyPostsAverageReport() {
        List<Map> report = reportService.getMonthlyPostsAverageReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/comments-analysis")
    public ResponseEntity<List<Map>> getCommentsAnalysisReport() {
        List<Map> report = reportService.getCommentsAnalysisReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = reportService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
}
