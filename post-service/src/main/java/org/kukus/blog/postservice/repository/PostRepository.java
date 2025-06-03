package org.kukus.blog.postservice.repository;

import org.kukus.blog.postservice.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    // Find all posts by a specific author
    List<Post> findByAuthorId(String authorId);
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

}