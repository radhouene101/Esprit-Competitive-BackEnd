package com.apex.picloud.dtos;

import com.apex.picloud.models.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostDTO {
    Long post_id;
    private String content;
    private Integer voteCount;
    private Integer LikesCount ;
    private Integer DislikesCount  ;
    private boolean pinned ;
    private LocalDateTime createdAt;
    private User createdBy;
    private LocalDateTime lastUpdateDate;
    private Topic topic ;


    public static PostDTO fromEntity(Post post)
    {
        return PostDTO.builder()
                .post_id(post.getPost_id())
                .content(post.getContent())
                .topic(post.getTopic())
                .build() ;
    }

    public static Post toEntity(PostDTO post)
    {
        return Post.builder()
                .post_id(post.getPost_id())
                .content(post.getContent())
                .createdBy(post.getCreatedBy())
                .topic(post.getTopic())
                .build() ;
    }
}
