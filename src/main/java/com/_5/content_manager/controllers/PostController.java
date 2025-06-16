package com._5.content_manager.controllers;

import com._5.content_manager.entities.Post;
import com._5.content_manager.entities.User;
import com._5.content_manager.services.CommentService;
import com._5.content_manager.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post,
                                           @RequestParam String authorId) {
        Post createdPost = postService.createPost(post, new User().builder().id(authorId).build());
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        Post post = postService.findById(id);
        postService.incrementViews(post.getId());
        return ResponseEntity.ok(post);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<Post> getPostBySlug(@PathVariable String slug) {
        Post post = postService.findBySlug(slug);
        postService.incrementViews(post.getId());
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getPublishedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publishedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Page<Post> posts = postService.getPublishedPosts(
                PageRequest.of(page, size, sort));
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<Post>> getPostsByAuthor(
            @PathVariable String authorId,
            @RequestParam(defaultValue = "published") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Post> posts = postService.getPostsByAuthor(
                authorId, status, PageRequest.of(page, size));
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/tags")
    public ResponseEntity<Page<Post>> getPostsByTags(
            @RequestParam List<String> tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Post> posts = postService.getPostsByTags(
                tags, PageRequest.of(page, size));
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Post>> getPostsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Post> posts = postService.getPostsByCategory(
                category, PageRequest.of(page, size));
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Post>> searchPosts(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Post> posts = postService.searchPosts(
                q, PageRequest.of(page, size));
        return ResponseEntity.ok(posts);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id,
                                           @RequestBody Post post) {
        Post updatedPost = postService.updatePost(id, post);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
