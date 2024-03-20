package com.apex.picloud.services;

import com.apex.picloud.models.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post);
    List<Post> getAllPosts();
    Post getPostById(Long post_id);
    Post updatePost(Post post);
    void deletePost(Long post_id);
}
