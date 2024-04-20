package com.apex.picloud.controllers;

import com.apex.picloud.dtos.ForumDTO;
import com.apex.picloud.models.Forum;
import com.apex.picloud.services.forum.ForumService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/forums")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ForumController {
    @Autowired
    private ForumService forumService ;
    @PostMapping("/addForum")
    //public Forum createForum(@RequestBody Forum forum){
     //   return forumService.createForum(forum);
    //}
    public ResponseEntity<ForumDTO> createForum(@RequestBody ForumDTO forum)
    { return ResponseEntity.ok(forumService.createForum(forum));}

    @PutMapping("/updateForum/{id}")
    public ResponseEntity<ForumDTO> updateForum(@PathVariable Long id, @RequestBody ForumDTO dto) {
        dto.setForum_id(id);
        ForumDTO updatedForumDTO = forumService.updateForum(dto);
        return ResponseEntity.ok(updatedForumDTO);
    }
    @GetMapping("/getForumById/{id}")
    public ResponseEntity<Forum> getForumById(@PathVariable Long id) {
        Forum forum = forumService.getForumById(id);
        return ResponseEntity.ok(forum);
    }
    @GetMapping("/getAllForums")
    public ResponseEntity<List<Forum>> getAllForums(){
        List<Forum> forums = forumService.getAllForums();
        Collections.sort(forums, new Comparator<Forum>() {
            @Override
            public int compare(Forum o1, Forum o2) {
                return o1.getForum_name().compareTo(o2.getForum_name());
            }
        });
        return ResponseEntity.ok(forums);
    }



    @DeleteMapping("/deleteForum/{id}")
    public ResponseEntity<?> deleteForum(@PathVariable Long id) {
        forumService.deleteForum(id);
        return ResponseEntity.noContent().build();
    }
}
