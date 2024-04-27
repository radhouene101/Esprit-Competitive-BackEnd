package com.apex.picloud.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="users")
@Data
public class User {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     private String name;
     private String email;
     private String password;
     private String phone;
     private int BadWordCount  ;
     private Boolean Banned = false;
     private Duration BanDuration ;
    private LocalDateTime banStartTime;

    public LocalDateTime getBanStartTime() {
        return banStartTime;
    }

    public void setBanStartTime(LocalDateTime banStartTime) {
        this.banStartTime = banStartTime;
    }

    public Boolean getBanned() {
        return Banned;
    }

    public Duration getBanDuration() {
        return BanDuration;
    }

    public void setBanDuration(Duration banDuration) {
        BanDuration = banDuration;
    }

    public void setBanned(Boolean banned) {
        Banned = banned;
    }


    public int getBadWordCount() {
        return BadWordCount;
    }

    public void setBadWordCount(int badWordCount) {
        BadWordCount = badWordCount;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post posts;

}
