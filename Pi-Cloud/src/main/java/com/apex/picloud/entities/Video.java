package com.apex.picloud.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.pibackend.entity.enums.Niveau;
import tn.esprit.pibackend.entity.enums.OptionEnum;
import tn.esprit.pibackend.entity.enums.Visible;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long videoId;
    private String videoTitle;
    private String videoDescription;
    private String videoUrl;
    private int videoViews;
    private int videoLikes;
    private int videoDislikes;
    private Date createdAt;
    private Date updatedAt;
    private Visible visibility;
    private Niveau niveau;
    private OptionEnum option;
    @Column(name = "video_thumbnail" , nullable = true)
    private String videoThumbnail;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "video_video_tags",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "video_tag_id")
    )
    private Set<VideoTag> tags = new HashSet<>();
}
