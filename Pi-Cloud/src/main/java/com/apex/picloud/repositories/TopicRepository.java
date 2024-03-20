package com.apex.picloud.repositories;

import com.apex.picloud.models.Topic;
import com.apex.picloud.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic,Long> {
}
