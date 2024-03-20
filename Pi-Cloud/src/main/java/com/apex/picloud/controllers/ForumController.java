package com.apex.picloud.controllers;

import com.apex.picloud.models.Forum;
import com.apex.picloud.services.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forums")
public class ForumController {
    @Autowired
    private ForumService forumService ;
    @PostMapping("/addForum")
    public Forum createForum(@RequestBody Forum forum){
        return forumService.createForum(forum);
    }
    @GetMapping("/getForumById/{id}")
    public ResponseEntity<Forum> getForumById(@PathVariable Long id) {
        Forum forum = forumService.getForumById(id);
        return ResponseEntity.ok(forum);
    }
    @GetMapping("/getAllForums")
    public ResponseEntity<List<Forum>> getAllForums(){
        List<Forum> forums = forumService.getAllForums();
        return ResponseEntity.ok(forums);
    }

    @PutMapping("/updateForum/{id}")
    public ResponseEntity<Forum> updateForum(@PathVariable Long id, @RequestBody Forum forum) {
        forum.setForum_id(id);
        Forum updatedForum = forumService.updateForum(forum);
        return ResponseEntity.ok(updatedForum);
    }

    @DeleteMapping("/deleteForum/{id}")
    public ResponseEntity<?> deleteForum(@PathVariable Long id) {
        forumService.deleteForum(id);
        return ResponseEntity.noContent().build();
    }
}
