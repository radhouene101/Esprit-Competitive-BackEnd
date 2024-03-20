package com.apex.picloud.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name="forums")
@Data
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="forum_id")
    private Long forum_id;
    @Column(nullable = false)
    String forum_name ;
    String description ;
    String created_by ;
    @CreatedDate
    LocalDateTime created_at ;

    public Long getForum_id() {
        return forum_id;
    }

    public void setForum_id(Long forum_id) {
        this.forum_id = forum_id;
    }
}
