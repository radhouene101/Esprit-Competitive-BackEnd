package com.apex.picloud.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;
import static org.springframework.data.jpa.domain.AbstractAuditable_.createdBy;

@Entity
@Table(name="posts")
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    Long post_id;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Integer voteCount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_id" , nullable = true)
    private Topic topic;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "name")
    private User user;


    @Column(updatable = false , nullable = false)
    @CreatedDate
    private Date createdAt;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Post() {
        // This constructor can be empty or you can initialize default values if needed
    }
    public Post(String content, Integer voteCount, Topic topic, User user, Date createdAt, List<Comment> comments, Date lastUpdateDate) {
        this.content = content;
        this.voteCount = voteCount;
        this.topic = topic;
        this.user = user;
        this.createdAt = createdAt;
        this.comments = comments;
        this.lastUpdateDate = lastUpdateDate;
    }
    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Topic getTopic() {
        return topic;
    }




    public void setTopic(Topic topic) {
        this.topic = topic;
    }



    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(nullable = false)
    private Date lastUpdateDate;
    public Post(String content, Topic topic, User createdBy, Date createdAt) {
        this.content = content;
        this.topic = topic;
        this.user = user;
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return Objects.equals(getPost_id(), post.getPost_id()) && Objects.equals(getContent(), post.getContent()) && Objects.equals(getVoteCount(), post.getVoteCount()) && Objects.equals(getTopic(), post.getTopic()) && Objects.equals(getUser(), post.getUser()) && Objects.equals(getCreatedAt(), post.getCreatedAt()) && Objects.equals(getComments(), post.getComments()) && Objects.equals(getLastUpdateDate(), post.getLastUpdateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPost_id(), getContent(), getVoteCount(), getTopic(), getUser(), getCreatedAt(), getComments(), getLastUpdateDate());
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
