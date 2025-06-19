package com._5.content_manager.dtos;

import com._5.content_manager.entities.Post;

import java.util.List;

public record DashboardDTO(
        long totalUsers,
        long totalPosts,
        long totalComments,
        double avgCommentsPerPost,
        List<Post> recentPosts
) {
}
