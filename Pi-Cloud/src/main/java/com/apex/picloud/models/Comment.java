package com.apex.picloud.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long comment_id;

    @Column(nullable = false)
    String content ;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

}
