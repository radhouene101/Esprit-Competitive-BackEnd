package com.apex.picloud.entities;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.pibackend.entity.enums.videoTags;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "video_tags")
public class VideoTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private videoTags tag;

    public VideoTag(videoTags tag) {
        this.tag = tag;
    }
}
