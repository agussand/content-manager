package com._5.content_manager.services;

import com._5.content_manager.entities.Comment;
import com._5.content_manager.entities.Post;
import com._5.content_manager.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataGeneratorService {
    List<User> generateUsers(List<User> users);
    List<Post> generatePosts();
    List<Comment> generateComments();
}
