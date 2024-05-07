package com.apex.picloud.dtos;

import com.apex.picloud.models.Forum;
import com.apex.picloud.models.Topic;
import com.apex.picloud.models.User;
import com.apex.picloud.services.topic.TopicService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@AllArgsConstructor

public class TopicDTO {
    private Long topic_id;
    private String title;
    private Forum forum;
    private User createdBy;
    private LocalDateTime createdAt;



    public static TopicDTO fromEntity(Topic topic)
    {
        return TopicDTO.builder()
                .topic_id(topic.getTopic_id())
                .title(topic.getTitle())
                .forum(topic.getForum())
                .createdBy(topic.getCreatedBy())
                .createdAt(topic.getCreatedAt())
                .build() ;
    }

    public static Topic toEntity(TopicDTO topic)
    {
        return Topic.builder()
                .topic_id(topic.getTopic_id())
                .title(topic.getTitle())
                .forum(topic.getForum())
                .createdBy(topic.getCreatedBy())
                .createdAt(topic.getCreatedAt())
                .build() ;
    }
    }



