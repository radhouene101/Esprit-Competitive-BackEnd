package com.apex.picloud.services.post;

import com.apex.picloud.dtos.PostDTO;
import com.apex.picloud.models.Comment;
import com.apex.picloud.models.Post;
import com.apex.picloud.repositories.CommentRepository;
import com.apex.picloud.repositories.PostRepository;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.services.contentModeration.ContentModerationService;
import com.apex.picloud.validator.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository ;
    @Autowired
    private ContentModerationService moderationService ;
    private UserRepository userRepository ;
    @Autowired
    private  final ObjectsValidator validator;
    @Autowired
    private CommentRepository commentRepository ;

     @Override
    public PostDTO createPost(PostDTO dto ) {
        validator.validate(dto) ;

        String content = dto.getContent();
        boolean containsForbiddenWords = moderationService.containsForbiddenWords(content);
         if (containsForbiddenWords) {
             content = moderationService.replaceForbiddenWords(content);
             dto.setContent(content);
         }

        Post post = PostDTO.toEntity(dto);
         postRepository.save(post);

         if (containsForbiddenWords) {
             moderationService.checkUserBadWordCount(dto.getCreatedBy());
         }

         return PostDTO.fromEntity(post);
    }


    @Override
    public List<Post> getAllPosts() {
        System.out.println(postRepository.findAll());
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long post_id) {
        Optional<Post> postOptional = postRepository.findById(post_id);
        return postOptional.orElse(null);
    }

    @Override
    public PostDTO updatePost(PostDTO dto) {
        Optional<Post> existingPostOptional = postRepository.findById(dto.getPost_id());
        if (existingPostOptional.isPresent()) {
            Post existingPost = existingPostOptional.get();
            existingPost.setContent(dto.getContent());
            existingPost.setCreatedBy(dto.getCreatedBy());
            existingPost = postRepository.save(existingPost);
            return PostDTO.fromEntity(existingPost);
        } else {
            // Handle the case where the post with the given ID is not found
            // You can throw an exception, return null, or handle it in any other appropriate way
            return null;
        }
    }

    @Override
    public void deletePost(Long post_id) {
        Optional<Post> existingPostOptional = postRepository.findById(post_id);
        if (!existingPostOptional.isPresent()) {
        }

        postRepository.deleteById(post_id);

    }
    public void likePost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            Integer currentLikes = post.getLikesCount(); // Get current likes count
            int current = (currentLikes != null) ? currentLikes : 0; // Initialize current likes with current count or 0
            post.setLikesCount(current + 1); // Increment likes count
            postRepository.save(post); // Save the updated post
        }
    }

    public void dislikePost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            Integer currentLikes = post.getDislikesCount(); // Get current likes count
            int current = (currentLikes != null) ? currentLikes : 0; // Initialize current likes with current count or 0
            post.setDislikesCount(current+1); // Increment likes count
            postRepository.save(post); // Save the updated post
        }
    }

    @Override
    public List<Post> getAllPostsByTopicId(Long topic_id) {
        return postRepository.findAllByTopicId(topic_id);
    }

    public void pinMostLikedPost() {
        postRepository.pinMostLikedPost();
    }

    public Post getPinnedPost() {
        return postRepository.findPinnedPost();
    }

    @Override
    public int getPostLikesCount(Long post_id) {
        Post post = postRepository.findById(post_id).orElse(null);
        if (post != null) {
            return post.getLikesCount();
        } else {
            return 0;
        }
     }

    @Override
    public int getPostDislikesCount(Long post_id) {
        Post post = postRepository.findById(post_id).orElse(null);
        if (post != null) {
            return post.getDislikesCount();
        } else {
            return 0;
        }     }


}
