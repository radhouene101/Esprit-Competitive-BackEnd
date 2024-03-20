package com.apex.picloud.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name="posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    Long post_id;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id")
    private Topic topic;
    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

}
