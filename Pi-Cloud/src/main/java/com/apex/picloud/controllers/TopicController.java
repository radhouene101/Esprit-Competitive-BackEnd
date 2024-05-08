package com.apex.picloud.controllers;

import com.apex.picloud.dtos.TopicDTO;
import com.apex.picloud.models.Topic;
import com.apex.picloud.services.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forums/topics")
public class TopicController {
    @Autowired
    private TopicService topicService ;

    @PostMapping("/addTopic")
    public ResponseEntity<TopicDTO> createPost(@RequestBody TopicDTO topic){

        return ResponseEntity.ok(topicService.createTopic(topic));
    }
    @GetMapping("/{forum_id}")
    public List<Topic> getAllTopicsByForumId(@PathVariable Long forum_id) {
        return topicService.getAllTopicByForumId(forum_id);
    }
    @GetMapping("/getTopicById/{id}")
    public ResponseEntity<Topic> getPostById(@PathVariable Long id) {
        Topic topic= topicService.getTopicById(id);
        return ResponseEntity.ok(topic);
    }
    @GetMapping("/getAllTopics")
    public ResponseEntity<List<Topic>> getAllTopics(){
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

    @PutMapping("/updateTopic/{id}")
    public ResponseEntity<TopicDTO> updateTopic(@PathVariable Long id, @RequestBody TopicDTO dto) {
        dto.setTopic_id(id);
        TopicDTO updatedTopicDTO = topicService.updateTopic(dto);
        return ResponseEntity.ok(updatedTopicDTO);
    }

    @DeleteMapping("/deleteTopic/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
