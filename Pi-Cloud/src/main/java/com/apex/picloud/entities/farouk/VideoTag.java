package com.apex.picloud.entities.farouk;

import com.apex.picloud.entities.farouk.enums.videoTags;
import jakarta.persistence.*;
import lombok.*;

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
