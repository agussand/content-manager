package com._5.content_manager.services;


import com._5.content_manager.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    Comment createComment(Comment comment, String authorId);
    Comment findById(String id);
    Page<Comment> getCommentsByPost(String postId, Pageable pageable);
    Page<Comment> getTopLevelCommentsByPost(String postId, Pageable pageable);
    Page<Comment> getCommentsByAuthor(String authorId, Pageable pageable);
    void deleteComment(String id);
    long getCommentsCountByPost(String postId);
}
