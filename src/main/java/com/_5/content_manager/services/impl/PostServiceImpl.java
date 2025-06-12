package com._5.content_manager.services.impl;

import com._5.content_manager.entities.Post;
import com._5.content_manager.entities.User;
import com._5.content_manager.models.post.AuthorInfo;
import com._5.content_manager.repositories.PostRepository;
import com._5.content_manager.services.PostService;
import com._5.content_manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;


    @Override
    public Post createPost(Post post, User author) {
        post.setAuthor(AuthorInfo.builder()
                        .userId(author.getId())
                        .username(author.getUsername())
                        .displayName(author.getProfile() != null ?
                                author.getProfile().getFirstName() + " " + author.getProfile().getLastName() :
                                author.getUsername())
                .build());

        // Generar slug si no existe
        if (post.getSlug() == null || post.getSlug().isEmpty()) {
            post.setSlug(generateSlug(post.getTitle()));
        }

        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        // Actualizar contador de posts del usuario
        author.getStats().setPostsCount(author.getStats().getPostsCount() + 1);
        userService.updateUser(author.getId(), author);

        return savedPost;
    }

    @Override
    public Post findById(String id) {
        return postRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "No existe un post con el id "+id));
    }

    @Override
    public Post findBySlug(String slug) {
        return postRepository.findBySlug(slug).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "No existe un usuario con el slug: "+slug));
    }

    @Override
    public Page<Post> getPublishedPosts(Pageable pageable) {
        return postRepository.findByStatusAndVisibility("published", "public", pageable);
    }

    @Override
    public Page<Post> getPostsByAuthor(String authorId, String status, Pageable pageable) {
        return postRepository.findByAuthorIdAndStatus(authorId, status, pageable);
    }

    @Override
    public Page<Post> getPostsByTags(List<String> tags, Pageable pageable) {
        return postRepository.findByTagsInAndStatusPublished(tags, pageable);
    }

    @Override
    public Page<Post> getPostsByCategory(String category, Pageable pageable) {
        return postRepository.findByCategoryAndStatusPublished(category, pageable);
    }

    @Override
    public Page<Post> searchPosts(String title, Pageable pageable) {
        return postRepository.findByTitleContainingIgnoreCaseAndStatusPublished(title, pageable);
    }

    @Override
    public Post updatePost(String id, Post updatedPost) {
        Post post = this.findById(id);
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setTags(updatedPost.getTags());
        post.setCategory(updatedPost.getCategory());
        post.setMedia(updatedPost.getMedia());
        post.setUpdatedAt(LocalDateTime.now());

        if (updatedPost.getSlug() != null && !updatedPost.getSlug().equals(post.getSlug())) {
            post.setSlug(generateSlug(updatedPost.getSlug()));
        }

        return postRepository.save(post);
    }

    @Override
    public Post updatePostCommentCount(Post post) {
        post.getStats().setCommentsCount(post.getStats().getCommentsCount() + 1);
        return postRepository.save(post);
    }

    @Override
    public Post incrementViews(String id) {
        Post post = this.findById(id);
        post.getStats().setViewsCount(post.getStats().getViewsCount() + 1);
        return postRepository.save(post);
    }

    @Override
    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    private String generateSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }
}
