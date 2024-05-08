package com.apex.picloud.dtos;

import com.apex.picloud.models.Forum;
import com.apex.picloud.models.Topic;
import com.apex.picloud.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ForumDTO {
    private Long forum_id;
    private String forum_name ;
    private String description ;
    private User createdBy ;
    private LocalDateTime created_at ;
    private List<Topic> topics ;
    public static ForumDTO fromEntity(Forum forum)
    {
        return ForumDTO.builder()
                .forum_id(forum.getForum_id())
                .forum_name(forum.getForum_name())
                .description(forum.getDescription())
                .createdBy(forum.getCreatedBy())
                .build() ;
    }

    public static Forum toEntity(ForumDTO forum)
    {
        return Forum.builder()
                .forum_id(forum.getForum_id())
                .forum_name(forum.getForum_name())
                .description(forum.getDescription())
                .createdBy(forum.getCreatedBy())
                .created_at(forum.getCreated_at())
                .build() ;
    }

}
