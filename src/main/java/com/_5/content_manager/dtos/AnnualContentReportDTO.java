package com._5.content_manager.dtos;

import java.util.List;

public record AnnualContentReportDTO(
        List<MonthlyStatDTO> monthlyStats,
        double avgPostsPerMonth,
        double avgAuthorsPerMonth
) {
}
