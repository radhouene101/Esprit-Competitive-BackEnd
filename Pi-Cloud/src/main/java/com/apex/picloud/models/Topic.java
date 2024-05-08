package com.apex.picloud.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="topics")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="topic_id")
    private Long topic_id;


    @Column(nullable = false)
    private String title;

    @ManyToOne(optional = false)
    @JoinColumn(name = "forum_id")
    private Forum forum;

    @ManyToOne
    @JoinColumn(name = "createdBy",nullable = false)
    private User createdBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    @Column
    private Boolean closed;

    @OneToMany(mappedBy = "post_id", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> posts;


}
