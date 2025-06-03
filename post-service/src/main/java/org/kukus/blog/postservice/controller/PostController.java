package org.kukus.blog.postservice.controller;

import org.kukus.blog.postservice.dto.PostDTO;
import org.kukus.blog.postservice.models.Post;
import org.kukus.blog.postservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PostDTO>> getRecentPosts(
            @RequestParam(defaultValue = "15") int size) {
        if (size <= 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(postService.getRecentPosts(size));
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.createPost(postDTO);
        return ResponseEntity.ok(createdPost);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable String id, @RequestParam String userId) {
        PostDTO updatedPost = postService.likePost(id, userId);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Post>> getPostsByAuthor(@PathVariable String authorId) {
        List<Post> posts = postService.getPostsByAuthor(authorId);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<PostDTO> addComment(
            @PathVariable String id,
            @RequestParam String authorId,
            @RequestParam String content) {
        PostDTO updatedPost = postService.addComment(id, authorId, content);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        }
        return ResponseEntity.notFound().build();
    }
}
