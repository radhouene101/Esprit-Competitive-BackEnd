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
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor

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


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "createdBy",nullable = false)
    private User createdBy;


    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    private Date lastUpdateDate;

    @Column
    private Boolean closed;


    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Post> posts;

    public Topic() {

    }
}
