package com.apex.picloud.services.topic;

import com.apex.picloud.dtos.TopicDTO;
import com.apex.picloud.models.Topic;

import java.util.List;

public interface TopicService {

    TopicDTO createTopic(TopicDTO topic);
    List<Topic> getAllTopic();
    Topic getTopicById(Long topic_id);
    TopicDTO updateTopic(TopicDTO topic);
    void deleteTopic(Long topic_id);

    List<Topic> getAllTopics();
}
