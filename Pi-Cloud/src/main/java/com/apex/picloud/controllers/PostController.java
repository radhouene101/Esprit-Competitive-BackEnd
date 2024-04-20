package com.apex.picloud.controllers;


import com.apex.picloud.dtos.PostDTO;
import com.apex.picloud.models.Post;
import com.apex.picloud.repositories.PostRepository;
import com.apex.picloud.services.contentModeration.ContentModerationService;
import com.apex.picloud.services.post.ContentValidationException;
import com.apex.picloud.services.post.PostService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forums/topics/posts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ContentModerationService moderationService;

    @PostMapping("/addPost")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO post){

        if (moderationService.containsForbiddenWords(post.getContent())) {
            throw new ContentValidationException("Post contains forbidden words.");
        }
        PostDTO savedPost = postService.createPost(post);
        return ResponseEntity.ok(savedPost);
    }

    @GetMapping("/getPostById/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post= postService.getPostById(id);
        return ResponseEntity.ok(post);
    }
    @GetMapping("/getAllPosts")
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/updatePost/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO dto) {
        if (moderationService.containsForbiddenWords(dto.getContent())) {
            throw new ContentValidationException("Post contains forbidden words.");

        }
        dto.setPost_id(id);

        PostDTO updatedPost = postService.updatePost(dto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
