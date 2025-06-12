package com._5.content_manager.repositories;


import com._5.content_manager.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Optional<Post> findBySlug(String slug);

    Page<Post> findByStatusAndVisibility(String status, String visibility, Pageable pageable);

    @Query("{'author.userId': ?0, 'status': ?1}")
    Page<Post> findByAuthorIdAndStatus(String authorId, String status, Pageable pageable);

    @Query("{'tags': {$in: ?0}, 'status': 'published'}")
    Page<Post> findByTagsInAndStatusPublished(List<String> tags, Pageable pageable);

    @Query("{'category': ?0, 'status': 'published'}")
    Page<Post> findByCategoryAndStatusPublished(String category, Pageable pageable);

    @Query("{'publishedAt': {$gte: ?0, $lte: ?1}, 'status': 'published'}")
    List<Post> findByPublishedAtBetweenAndStatusPublished(LocalDateTime start, LocalDateTime end);

    @Query(value = "{'title': {$regex: ?0, $options: 'i'}, 'status': 'published'}")
    Page<Post> findByTitleContainingIgnoreCaseAndStatusPublished(String title, Pageable pageable);
}
