package com.apex.picloud.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name="topics")
@Data
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
    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;
}
