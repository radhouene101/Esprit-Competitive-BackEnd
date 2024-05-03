package com.apex.picloud.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany
    private List<Forum> forums ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post posts;

    private boolean active;
    private String otp;
    private LocalDateTime OtpGeneratedTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Status status;





}
