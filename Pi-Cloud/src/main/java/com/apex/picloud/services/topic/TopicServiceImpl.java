package com.apex.picloud.services.topic;

import com.apex.picloud.dtos.ForumDTO;
import com.apex.picloud.dtos.TopicDTO;
import com.apex.picloud.models.Forum;
import com.apex.picloud.models.Topic;
import com.apex.picloud.repositories.TopicRepository;
import com.apex.picloud.services.topic.TopicService;
import com.apex.picloud.validator.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private  final ObjectsValidator validator;


    @Override
    public TopicDTO createTopic(TopicDTO dto) {
        validator.validate(dto);
        Topic topic1 = TopicDTO.toEntity(dto);
        topicRepository.save(topic1);
        return TopicDTO.fromEntity(topic1);
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
    public TopicDTO updateTopic(TopicDTO dto) {
        Optional<Topic> existingTopicOptional = topicRepository.findById(dto.getTopic_id());
        if (existingTopicOptional.isPresent()) {
            Topic existingTopic = existingTopicOptional.get();
            existingTopic.setTitle(dto.getTitle());
            existingTopic.setForum(dto.getForum());
            existingTopic.setCreatedBy(dto.getCreatedBy());
            existingTopic.setCreatedAt(dto.getCreatedAt());
            existingTopic = topicRepository.save(existingTopic);
            return TopicDTO.fromEntity(existingTopic);
        } else {
            throw new EntityNotFoundException("Topic not found with id: " + dto.getTopic_id());
        }
    }

    @Override
    public void deleteTopic(Long topic_id) {
        Optional<Topic> existingTopicOptional = topicRepository.findById(topic_id);
        if (!existingTopicOptional.isPresent()) {
        }

        topicRepository.deleteById(topic_id);
    }

    @Override
    public List<Topic> getAllTopicByForumId(Long forum_id) {
        return topicRepository.findAllByForumId(forum_id);
    }


    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();}
}
