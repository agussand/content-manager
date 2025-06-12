package com._5.content_manager.repositories;



import com._5.content_manager.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByStatus(String status);

    @Query("{'interests': {$in: ?0}}")
    List<User> findByInterestsIn(List<String> interests);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
