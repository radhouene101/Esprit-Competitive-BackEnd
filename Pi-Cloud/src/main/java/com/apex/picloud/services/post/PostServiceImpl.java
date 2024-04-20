package com.apex.picloud.services.post;

import com.apex.picloud.dtos.PostDTO;
import com.apex.picloud.models.Post;
import com.apex.picloud.models.User;
import com.apex.picloud.repositories.PostRepository;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.services.contentModeration.ContentModerationService;
import com.apex.picloud.services.post.PostService;
import com.apex.picloud.validator.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository ;
    @Autowired
    private ContentModerationService moderationService ;
    private UserRepository userRepository ;
    @Autowired
    private  final ObjectsValidator validator;



    @Override
    public PostDTO createPost(PostDTO dto) {
        validator.validate(dto) ;
        Post post = PostDTO.toEntity(dto);
         postRepository.save(post);
         return PostDTO.fromEntity(post);
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
    public PostDTO updatePost(PostDTO dto) {
        Optional<Post> existingPostOptional = postRepository.findById(dto.getPost_id());
        if (existingPostOptional.isPresent()) {
            Post existingPost = existingPostOptional.get();
            existingPost.setContent(dto.getContent());
            existingPost.setUser(dto.getCreatedBy());
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
}
