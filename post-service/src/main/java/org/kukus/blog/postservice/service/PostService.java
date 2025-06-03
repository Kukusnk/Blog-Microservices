package org.kukus.blog.postservice.service;

import org.kukus.blog.postservice.dto.PostDTO;
import org.kukus.blog.postservice.models.Comment;
import org.kukus.blog.postservice.models.Post;
import org.kukus.blog.postservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PostService(PostRepository postRepository, MongoTemplate mongoTemplate) {
        this.postRepository = postRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public PostDTO createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAuthorId(postDTO.getAuthorId());
        post.setPhotoUrls(postDTO.getPhotoUrls());
        Post savedPost = postRepository.save(post);
        return new PostDTO(savedPost);
    }

    public PostDTO likePost(String postId, String userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (!post.getLikedBy().contains(userId)) {
                post.getLikedBy().add(userId);
                post.setLikes(post.getLikes() + 1);
                Post updatedPost = postRepository.save(post);
                return new PostDTO(updatedPost);
            }
        }
        return null; // Post not found or already liked
    }

    public List<Post> getPostsByAuthor(String authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            postDTOs.add(new PostDTO(post));
        }
        return postDTOs;
    }

//    public List<PostDTO> getRecentPosts(int page, int size) {
//        Pageable pageable = PageRequest.of(page-1, size);
//        List<Post> posts = postRepository.findAll(pageable).getContent();
//        List<PostDTO> postDTOs = new ArrayList<>();
//        for (Post post : posts) {
//            postDTOs.add(new PostDTO(post));
//        }
//        return postDTOs;
//    }


    public List<PostDTO> getRecentPosts(int size) {
        Query query = new Query()
                .with(Sort.by(Sort.Direction.DESC, "createdAt"))
                .limit(size);

        List<Post> posts = mongoTemplate.find(query, Post.class);
        return posts.stream().map(PostDTO::new).toList();
    }

    public PostDTO addComment(String postId, String authorId, String content) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            Comment comment = new Comment();
            comment.setAuthorId(authorId);
            comment.setContent(content);
            post.getComments().add(comment);
            Post updatedPost = postRepository.save(post);
            return new PostDTO(updatedPost);
        }
        return null; // Post not found
    }


}
