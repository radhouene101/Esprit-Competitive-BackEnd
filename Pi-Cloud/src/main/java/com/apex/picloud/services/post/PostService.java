package com.apex.picloud.services.post;

import com.apex.picloud.dtos.PostDTO;
import com.apex.picloud.models.Comment;
import com.apex.picloud.models.Post;
import com.apex.picloud.models.Topic;
import org.springframework.messaging.MessagingException;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO post ) throws MessagingException;
    List<Post> getAllPosts();
    Post getPostById(Long post_id);
    PostDTO updatePost(PostDTO post);
    void deletePost(Long post_id);
    void likePost(Long postId);
    void dislikePost(Long postId);
    List<Post> getAllPostsByTopicId(Long topic_id);
    void pinMostLikedPost();
    Post getPinnedPost();
    int getPostLikesCount(Long post_id);
    int getPostDislikesCount(Long post_id);

}
