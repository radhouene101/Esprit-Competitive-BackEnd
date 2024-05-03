package com.apex.picloud.dtos;

import com.apex.picloud.models.Comment;
import com.apex.picloud.models.Forum;
import com.apex.picloud.models.Post;
import com.apex.picloud.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.support.ApplicationObjectSupport;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long comment_id;
    private String content ;
    private User createdBy;
    private Post post ;



    public static CommentDTO fromEntity(Comment comment)
    {
        return CommentDTO.builder()
                .comment_id(comment.getComment_id())
                .content(comment.getContent())
                .createdBy(comment.getCreatedBy())
                .post(comment.getPost())
                .build() ;
    }

    public static Comment toEntity(CommentDTO comment)
    {
        return Comment.builder()
                .comment_id(comment.getComment_id())
                .content(comment.getContent())
                .createdBy(comment.getCreatedBy())
                .post(comment.getPost())
                .build() ;
    }

}
