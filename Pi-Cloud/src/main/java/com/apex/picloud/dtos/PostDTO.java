package com.apex.picloud.dtos;




import com.apex.picloud.models.Forum;
import com.apex.picloud.models.Post;
import com.apex.picloud.models.Topic;
import com.apex.picloud.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostDTO {
    Long post_id;
    private String content;
    private Topic topic;
    private User createdBy;
    private LocalDateTime createdAt;

    public static PostDTO fromEntity(Post post)
    {
        return PostDTO.builder()
                .post_id(post.getPost_id())
                .content(post.getContent())
                .build() ;
    }

    public static Post toEntity(PostDTO post)
    {
        return Post.builder()
                .post_id(post.getPost_id())
                .content(post.getContent())
                .user(post.getCreatedBy())
                .build() ;
    }
}
