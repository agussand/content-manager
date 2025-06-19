package com._5.content_manager.dtos;

public record MonthlyStatDTO(
        String period,
        int postsCount,
        int uniqueAuthors,
        long totalViews
) {
}
