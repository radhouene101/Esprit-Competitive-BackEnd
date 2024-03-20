package com.apex.picloud.services;

import com.apex.picloud.models.Post;
import com.apex.picloud.models.Topic;
import com.apex.picloud.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService{
    @Autowired
    private TopicRepository topicRepository;
    @Override
    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public List<Topic> getAllTopic() {
        return topicRepository.findAll();
    }

    @Override
    public Topic getTopicById(Long topic_id) {
        Optional<Topic> topicOptional = topicRepository.findById(topic_id);
        return  topicOptional.orElse(null);
    }

    @Override
    public Topic updateTopic(Topic topic) {
        Optional<Topic> existingTopicOptional = topicRepository.findById(topic.getTopic_id());
        if (!existingTopicOptional.isPresent()) {
        }

        Topic existingTopic = existingTopicOptional.get();
        existingTopic.setTitle(topic.getTitle());
        existingTopic.setCreatedBy(topic.getCreatedBy());

        topicRepository.save(existingTopic);
        return existingTopic;
    }

    @Override
    public void deleteTopic(Long topic_id) {
        Optional<Topic> existingTopicOptional = topicRepository.findById(topic_id);
        if (!existingTopicOptional.isPresent()) {
        }

        topicRepository.deleteById(topic_id);
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();}
}
