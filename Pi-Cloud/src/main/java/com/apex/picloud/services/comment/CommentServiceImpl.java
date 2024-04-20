package com.apex.picloud.services.comment;

import com.apex.picloud.dtos.CommentDTO;
import com.apex.picloud.dtos.ForumDTO;
import com.apex.picloud.models.Comment;
import com.apex.picloud.models.Forum;
import com.apex.picloud.repositories.CommentRepository;
import com.apex.picloud.services.comment.CommentService;
import com.apex.picloud.validator.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository ;
    @Autowired
    private  final ObjectsValidator validator;
    @Override
    public CommentDTO createComment(CommentDTO dto) {
        validator.validate(dto);
        Comment comment1 = CommentDTO.toEntity(dto);
        commentRepository.save(comment1);
        return CommentDTO.fromEntity(comment1) ;
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
    public CommentDTO updateComment(CommentDTO dto) {

        Optional<Comment> existingCommentOptional = commentRepository.findById(dto.getComment_id());
        if (existingCommentOptional.isPresent()) {
            Comment existingComment = existingCommentOptional.get();
            existingComment.setContent(dto.getContent());
            return CommentDTO.fromEntity(existingComment);
        } else {
            return null;
        }
    }

    @Override
    public void deleteComment(Long comment_id) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(comment_id);
        if (!existingCommentOptional.isPresent()) {
        }
        commentRepository.deleteById(comment_id);
    }

    @Override
    public Comment addNestedComment(Long parentId, Comment nestedComment) {
        Comment parentComment = commentRepository.findById(parentId).orElse(null);
        if (parentComment == null) {
            throw new NotFoundException("Parent comment not found");
        }
        parentComment.addNestedComment(nestedComment); // Assuming addNestedComment method is defined in Comment class
        return commentRepository.save(parentComment);
    }

    @Override
    public Comment getCommentWithNested(Long id) {
        return commentRepository.findById(id).orElse(null);    }

    @Override
    public Comment updateNestedComment(Long parentId, Comment updatedNestedComment) {
        Comment parentComment = commentRepository.findById(parentId).orElse(null);
        if (parentComment == null) {
            throw new NotFoundException("Parent comment not found");
        }
        parentComment.updateNestedComment(updatedNestedComment);
        return commentRepository.save(parentComment);    }

    @Override
    public void deleteNestedComment(Long parentId, Long nestedId) {
        Comment parentComment = commentRepository.findById(parentId).orElse(null);
        if (parentComment == null) {
            throw new NotFoundException("Parent comment not found");
        }
        parentComment.deleteNestedComment(nestedId);
        commentRepository.save(parentComment);
    }
}
