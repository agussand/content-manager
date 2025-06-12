package com._5.content_manager.repositories;


import com._5.content_manager.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    Page<Comment> findByPostId(String postId, Pageable pageable);

    @Query("{'author.userId': ?0}")
    Page<Comment> findByAuthorId(String authorId, Pageable pageable);

    @Query("{'postId': ?0}")
    Page<Comment> findTopLevelCommentsByPostId(String postId, Pageable pageable);

    long countByPostId(String postId);
}
