package com._5.content_manager.controllers;


import com._5.content_manager.dtos.*;
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
    public ResponseEntity<List<UserPostCountDTO>> getPostsByUserReport() {
        List<UserPostCountDTO> report = reportService.getPostsByUserReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/top-commented-posts")
    public ResponseEntity<List<TopCommentedPostDTO>> getTopCommentedPostsReport() {
        List<TopCommentedPostDTO> report = reportService.getTopCommentedPostsReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/monthly-average")
    public ResponseEntity<AnnualContentReportDTO> getMonthlyPostsAverageReport() {
        AnnualContentReportDTO report = reportService.getMonthlyPostsAverageReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/post-engagement")
    public ResponseEntity<List<PostEngagementDTO>> getPostEngagementReport() {
        List<PostEngagementDTO> reportData = reportService.getPostEngagementData();
        return ResponseEntity.ok(reportData);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> getDashboardStats() {
        DashboardDTO stats = reportService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
}
