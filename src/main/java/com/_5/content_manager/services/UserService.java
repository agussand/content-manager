package com._5.content_manager.services;


import com._5.content_manager.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User createUser(User user);
    List<User> createMany(List<User> users);
    User findByUsername(String username);
    User findById(String id);
    User findByEmail(String email);
    User updateUser(String id, User updatedUser);
    void updateLastLogin(String id);
    void incrementPostCounting(String user);
    List<User> findByInterests(List<String> interests);
    List<User> getAllActiveUsers();
    void deleteUser(String id);
}
