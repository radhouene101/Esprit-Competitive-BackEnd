package com.apex.picloud.services;

import com.apex.picloud.models.Post;
import com.apex.picloud.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository ; 
    @Override
    public Post createPost(Post post) {
       return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long post_id) {
        Optional<Post> postOptional = postRepository.findById(post_id);
        return postOptional.orElse(null);
    }

    @Override
    public Post updatePost(Post post) {
        Optional<Post> existingPostOptional = postRepository.findById(post.getPost_id());
        if (!existingPostOptional.isPresent()) {
        }

        Post existingPost = existingPostOptional.get();
        // Update specific fields if needed (e.g., name, description)
        existingPost.setContent(post.getContent());
        existingPost.setCreatedBy(post.getCreatedBy());

        // Save the updated forum
        postRepository.save(existingPost);
        return existingPost;
    }

    @Override
    public void deletePost(Long post_id) {
        Optional<Post> existingPostOptional = postRepository.findById(post_id);
        if (!existingPostOptional.isPresent()) {
        }

        postRepository.deleteById(post_id);

    }
}
