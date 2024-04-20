package com.apex.picloud.dtos;

import com.apex.picloud.models.Forum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ForumDTO {
    private Long forum_id;
    private String forum_name ;
    private String description ;
    private String created_by ;
    private LocalDateTime created_at ;



    public static ForumDTO fromEntity(Forum forum)
    {
        return ForumDTO.builder()
                .forum_id(forum.getForum_id())
                .forum_name(forum.getForum_name())
                .description(forum.getDescription())
                .created_by(forum.getCreated_by())
                .build() ;
    }

    public static Forum toEntity(ForumDTO forum)
    {
        return Forum.builder()
                .forum_id(forum.getForum_id())
                .forum_name(forum.getForum_name())
                .description(forum.getDescription())
                .created_by(forum.getCreated_by())
                .created_at(forum.getCreated_at())
                .build() ;
    }

}
