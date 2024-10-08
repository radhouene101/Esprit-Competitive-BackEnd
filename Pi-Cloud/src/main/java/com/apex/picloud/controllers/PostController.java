package com.apex.picloud.controllers;


import com.apex.picloud.dtos.PostDTO;
import com.apex.picloud.models.Comment;
import com.apex.picloud.models.Post;
import com.apex.picloud.models.Topic;
import com.apex.picloud.models.User;
import com.apex.picloud.repositories.PostRepository;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.services.contentModeration.ContentModerationService;
import com.apex.picloud.services.post.ContentValidationException;
import com.apex.picloud.services.post.PostService;
import com.apex.picloud.services.post.DiscordNotifier;

import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    private ContentModerationService moderationService ;
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private DiscordNotifier discordNotifier;

   /* @PostMapping("/addPost")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO post) throws MessagingException{

        if (moderationService.containsForbiddenWords(post.getContent())) {
            User user = post.getCreatedBy(); // Retrieve the User object from the post
            if (user != null) {
                logger.info("User object retrieved: {}", user);
                moderationService.checkUserBadWordCount(user); // Increment bad word count
                logger.info("Bad word count after increment: {}", user.getBadWordCount());            }
            throw new ContentValidationException("Post contains forbidden words.");
        }
        PostDTO savedPost = postService.createPost(post);
        return ResponseEntity.ok(savedPost);
    }*/
   @PostMapping("/addPost")
   public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO post) throws MessagingException {
       String originalContent = post.getContent();
       String sanitizedContent = moderationService.replaceForbiddenWords(post.getContent());
       post.setContent(sanitizedContent);
       if (moderationService.containsForbiddenWords(sanitizedContent)) {
           User user = post.getCreatedBy();
           if (user != null) {
               logger.info("User object retrieved: {}", user);
               moderationService.checkUserBadWordCount(user);
               logger.info("Bad word count after increment: {}", user.getBadWordCount());
           }
           throw new ContentValidationException("Post contains forbidden words.");
       }
       PostDTO savedPost = postService.createPost(post);

       if (!originalContent.equals(sanitizedContent)) {
           User user = post.getCreatedBy();
           if (user != null) {
               logger.info("User object retrieved: {}", user);
               moderationService.checkUserBadWordCount(user);
               logger.info("Bad word count after increment: {}", user.getBadWordCount());
           }
       }
       return ResponseEntity.ok(savedPost);
   }

    @PostMapping("/posts/{postId}/shareToDiscord")
    public void sharePostToDiscord(@PathVariable("postId") Long postId) throws JSONException {
        Post post = postService.getPostById(postId);
        if (post != null) {
            discordNotifier.sharePostToDiscord(post);
        } else {

        }
    }
    @GetMapping("/{topic_id}")
    public List<Post> getAllPostsByTopicId(@PathVariable Long topic_id) {
        return postService.getAllPostsByTopicId(topic_id);
    }


    @GetMapping("/getPostById/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post= postService.getPostById(id);
        return ResponseEntity.ok(post);
    }
    @GetMapping("/getAllPosts")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
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

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable("postId") Long postId) {
        postService.likePost(postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/posts/{postId}/dislike")
    public ResponseEntity<Post> dislikePost(@PathVariable("postId") Long postId) {
        postService.dislikePost(postId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/pinMostLikedPost")
    public ResponseEntity<?> pinMostLikedPost() {
        postService.pinMostLikedPost();
        return ResponseEntity.ok().build();
    }

    // Endpoint to retrieve the pinned post
    @GetMapping("/pinned")
    public ResponseEntity<?> getPinnedPost() {
        Post pinnedPost = postService.getPinnedPost();
        if (pinnedPost != null) {
            return ResponseEntity.ok().body(pinnedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{postId}/likes")
    public int getPostLikesCount(@PathVariable Long postId) {
        return postService.getPostLikesCount(postId);
    }

    @GetMapping("/{postId}/dislikes")
    public int getPostDislikesCount(@PathVariable Long postId) {
        return postService.getPostDislikesCount(postId);
    }
}
