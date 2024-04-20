package com.apex.picloud.services.post;

import com.apex.picloud.dtos.PostDTO;
import com.apex.picloud.models.Post;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO post);
    List<Post> getAllPosts();
    Post getPostById(Long post_id);
    PostDTO updatePost(PostDTO post);
    void deletePost(Long post_id);
}
