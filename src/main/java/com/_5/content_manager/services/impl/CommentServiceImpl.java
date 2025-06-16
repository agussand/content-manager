package com._5.content_manager.services.impl;


import com._5.content_manager.entities.Comment;
import com._5.content_manager.entities.Post;
import com._5.content_manager.entities.User;
import com._5.content_manager.models.post.AuthorInfo;
import com._5.content_manager.repositories.CommentRepository;
import com._5.content_manager.services.CommentService;
import com._5.content_manager.services.PostService;
import com._5.content_manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Override
    public Comment createComment(Comment comment, String authorId) {
        User author = userService.findById(authorId);
        Post post = postService.findById(comment.getPostId());
        comment.setAuthor(AuthorInfo.builder()
                        .userId(author.getId())
                        .username(author.getUsername())
                        .displayName(author.getProfile() != null ?
                                author.getProfile().getFirstName() + " " + author.getProfile().getLastName() :
                                author.getUsername())
                .build());

        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());

        postService.updatePostCommentCount(post);

        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(String id) {
        return commentRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "No existe un comentario con el id: "+id));
    }

    @Override
    public Page<Comment> getCommentsByPost(String postId, Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }

    @Override
    public Page<Comment> getTopLevelCommentsByPost(String postId, Pageable pageable) {
        return commentRepository.findTopLevelCommentsByPostId(postId, pageable);
    }

    @Override
    public Page<Comment> getCommentsByAuthor(String authorId, Pageable pageable) {
        return commentRepository.findByAuthorId(authorId, pageable);
    }

    @Override
    public void deleteComment(String id) {
        commentRepository.deleteById(id);
    }

    @Override
    public long getCommentsCountByPost(String postId) {
        return commentRepository.countByPostId(postId);
    }
}
