package com._5.content_manager.services.impl;


import com._5.content_manager.entities.User;
import com._5.content_manager.repositories.UserRepository;
import com._5.content_manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;



    @Override
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username ya existe: " + user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email ya existe: " + user.getEmail());
        }

        user.setPasswordHash(user.getPasswordHash());
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public List<User> createMany(List<User> users) {
        return userRepository.saveAll(users);
    }

    @Override
    public User findById(String id){
        return userRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "No existe un usuario con el id "+id));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "No existe un usuario con el usuario "+username));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "No existe un usuario con el email "+email));
    }

    @Override
    public User updateUser(String id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    if (updatedUser.getProfile() != null) {
                        user.setProfile(updatedUser.getProfile());
                    }
                    if (updatedUser.getInterests() != null) {
                        user.setInterests(updatedUser.getInterests());
                    }
                    if (updatedUser.getSocialLinks() != null) {
                        user.setSocialLinks(updatedUser.getSocialLinks());
                    }
                    if (updatedUser.getSettings() != null) {
                        user.setSettings(updatedUser.getSettings());
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
    }

    @Override
    public void updateLastLogin(String id) {
        userRepository.findById(id)
                .ifPresent(user -> {
                    user.setLastLogin(LocalDateTime.now());
                    userRepository.save(user);
                });
    }

    @Override
    public void incrementPostCounting(String userId) {
        User user = this.findById(userId);
        user.getStats().setPostsCount(user.getStats().getPostsCount() + 1);
        userRepository.save(user);
    }

    @Override
    public List<User> findByInterests(List<String> interests) {
        return userRepository.findByInterestsIn(interests);
    }

    @Override
    public List<User> getAllActiveUsers() {
        return userRepository.findByStatus("active");
    }

    @Override
    public void deleteUser(String id) {
        userRepository.findById(id)
                .ifPresent(user -> {
                    user.setStatus("deleted");
                    userRepository.save(user);
                });
    }
}
