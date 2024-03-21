package com.apex.picloud.services;

import com.apex.picloud.models.Comment;
import com.apex.picloud.models.Forum;
import com.apex.picloud.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository ;
    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment) ;
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(Long comment_id) {
        Optional<Comment> commentOptional = commentRepository.findById(comment_id);
        return commentOptional.orElse(null);
    }

    @Override
    public Comment updateComment(Comment comment) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(comment.getComment_id());
        if (!existingCommentOptional.isPresent()) {
        }

        Comment existingComment = existingCommentOptional.get();
        existingComment.setContent(comment.getContent());

        commentRepository.save(existingComment);
        return existingComment;    }

    @Override
    public void deleteComment(Long comment_id) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(comment_id);
        if (!existingCommentOptional.isPresent()) {
        }
        commentRepository.deleteById(comment_id);
    }
}
