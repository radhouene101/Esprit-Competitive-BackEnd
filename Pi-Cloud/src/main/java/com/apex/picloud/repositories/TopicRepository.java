package com.apex.picloud.repositories;

import com.apex.picloud.models.Topic;
import com.apex.picloud.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic,Long> {
    @Query("SELECT t FROM Topic t WHERE t.forum.forum_id = :forumId")
    List<Topic> findAllByForumId(Long forumId);
}