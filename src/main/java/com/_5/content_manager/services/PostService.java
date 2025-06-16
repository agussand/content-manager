package com._5.content_manager.services;


import com._5.content_manager.entities.Post;
import com._5.content_manager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    Post createPost(Post post, User author);
    Post findById(String id);
    Post findBySlug(String slug);
    List<Post> allPosts();
    Page<Post> getPublishedPosts(Pageable pageable);
    Page<Post> getPostsByAuthor(String authorId, String status, Pageable pageable);
    Page<Post> getPostsByTags(List<String> tags, Pageable pageable);
    Page<Post> getPostsByCategory(String category, Pageable pageable);
    Page<Post> searchPosts(String title, Pageable pageable);
    Post updatePost(String id, Post updatedPost);
    void updatePostCommentCount(Post post);
    void incrementViews(String id);
    void deletePost(String id);
}
