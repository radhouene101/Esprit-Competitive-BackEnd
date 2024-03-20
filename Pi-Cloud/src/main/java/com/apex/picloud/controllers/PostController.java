package com.apex.picloud.controllers;


import com.apex.picloud.models.Post;
import com.apex.picloud.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forums/topics/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/addPost")
    public Post createPost(@RequestBody Post post){

        return postService.createPost(post);
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
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        post.setPost_id(id);
        Post updatedForum = postService.updatePost(post);
        return ResponseEntity.ok(updatedForum);
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
