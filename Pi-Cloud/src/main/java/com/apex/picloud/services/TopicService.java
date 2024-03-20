package com.apex.picloud.services;

import com.apex.picloud.models.Topic;

import java.util.List;

public interface TopicService {

    Topic createTopic(Topic topic);
    List<Topic> getAllTopic();
    Topic getTopicById(Long topic_id);
    Topic updateTopic(Topic topic);
    void deleteTopic(Long topic_id);

    List<Topic> getAllTopics();
}
