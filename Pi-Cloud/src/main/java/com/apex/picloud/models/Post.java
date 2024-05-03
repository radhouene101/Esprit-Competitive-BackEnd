package com.apex.picloud.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static org.springframework.data.jpa.domain.AbstractAuditable_.createdBy;

@Entity
@Table(name="posts")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    Long post_id;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Integer voteCount;
    private Integer LikesCount = 0 ;
    private Integer DislikesCount = 0 ;
    private boolean pinned ;
    private LocalDateTime createdAt;
    @Nullable
    private LocalDateTime lastUpdateDate;

    @ManyToOne
    @JoinColumn(name = "topic_id" , nullable = true)
    private Topic topic;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User createdBy;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments;








    public void setLikesCount(Integer likesCount) {
        LikesCount = likesCount;
    }



    public void setDislikesCount(Integer dislikesCount) {
        DislikesCount = dislikesCount;
    }




}
