package com.apex.picloud.services.comment;

import com.apex.picloud.dtos.CommentDTO;
import com.apex.picloud.models.Comment;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(CommentDTO comment);
    List<Comment> getAllComments();
    Comment getCommentById(Long comment_id);
    CommentDTO updateComment(CommentDTO comment);

    void deleteComment(Long comment_id);

    Comment addNestedComment(Long parentId, Comment nestedComment);

    Comment getCommentWithNested(Long id);

    Comment updateNestedComment(Long parentId, Comment nestedComment);

    void deleteNestedComment(Long parentId, Long nestedId);
    List<Comment> getCommentsByPostId(Long postId);
}