package com.apex.picloud.services;

import com.apex.picloud.models.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);
    List<Comment> getAllComments();
    Comment getCommentById(Long comment_id);
    Comment updateComment(Comment comment);

    void deleteComment(Long comment_id);
}
