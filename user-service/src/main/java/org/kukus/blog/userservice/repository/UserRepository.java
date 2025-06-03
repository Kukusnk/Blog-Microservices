package org.kukus.blog.userservice.repository;

import org.kukus.blog.userservice.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

// Repository for performing CRUD operations on User documents
public interface UserRepository extends MongoRepository<User, String> {
    // Find user by email
    User findByEmail(String email);

    // Find all users from the list of ids
    List<User> findAllByIdIn(Set<String> ids);
}
