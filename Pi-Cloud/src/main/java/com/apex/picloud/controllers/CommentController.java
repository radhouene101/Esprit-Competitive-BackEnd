package com.apex.picloud.controllers;

import com.apex.picloud.dtos.CommentDTO;
import com.apex.picloud.models.Comment;
import com.apex.picloud.services.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forums/topics/posts/comments")
public class CommentController {
    @Autowired
    private CommentService commentService ;

    @PostMapping("/addComment")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment){
        return ResponseEntity.ok(commentService.createComment(comment));
    }
    @GetMapping("/getCommentById/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }
    @GetMapping("/getAllComments")
    public ResponseEntity<List<Comment>> getAllComments(){
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/updateComment/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO dto) {
        dto.setComment_id(id);
        CommentDTO updatedCommentDTO = commentService.updateComment(dto);
        return ResponseEntity.ok(updatedCommentDTO);
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/addNestedComment/{parentId}")
    public Comment addNestedComment(@PathVariable Long parentId, @RequestBody Comment nestedComment) {
        return commentService.addNestedComment(parentId, nestedComment);
    }

    // Endpoint for retrieving comments along with nested comments
    @GetMapping("/getCommentWithNested/{id}")
    public ResponseEntity<Comment> getCommentWithNested(@PathVariable Long id) {
        Comment comment = commentService.getCommentWithNested(id);
        return ResponseEntity.ok(comment);
    }

    // Endpoint for updating nested comments
    @PutMapping("/updateNestedComment/{parentId}/{nestedId}")
    public ResponseEntity<Comment> updateNestedComment(@PathVariable Long parentId, @PathVariable Long nestedId, @RequestBody Comment nestedComment) {
        nestedComment.setComment_id(nestedId);
        Comment updatedComment = commentService.updateNestedComment(parentId, nestedComment);
        return ResponseEntity.ok(updatedComment);
    }

    // Endpoint for deleting nested comments
    @DeleteMapping("/deleteNestedComment/{parentId}/{nestedId}")
    public ResponseEntity<?> deleteNestedComment(@PathVariable Long parentId, @PathVariable Long nestedId) {
        commentService.deleteNestedComment(parentId, nestedId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}