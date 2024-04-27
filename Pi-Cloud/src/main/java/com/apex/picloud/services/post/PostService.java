package com.apex.picloud.services.post;

import com.apex.picloud.dtos.PostDTO;
import com.apex.picloud.models.Post;
import com.apex.picloud.models.User;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO post ) throws MessagingException;
    List<Post> getAllPosts();
    Post getPostById(Long post_id);
    PostDTO updatePost(PostDTO post);
    void deletePost(Long post_id);
    void likePost(Long postId);
    void dislikePost(Long postId);

    void pinMostLikedPost();

    Post getPinnedPost();
}
