package com.apex.picloud.controllers;

import com.apex.picloud.models.Comment;
import com.apex.picloud.services.CommentService;
import com.apex.picloud.services.ForumService;
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
    public Comment createComment(@RequestBody Comment comment){
        return commentService.createComment(comment);
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
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        comment.setComment_id(id);
        Comment updatedComment = commentService.updateComment(comment);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
