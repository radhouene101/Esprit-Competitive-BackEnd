package com.apex.picloud.models;

import com.apex.picloud.entities.Projects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="users",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails{


    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     private String name;

     @Column(unique = true)
     private String email;
     private String password;
     private boolean active;
     private String otp;
     private LocalDateTime OtpGeneratedTime;
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    @JsonIgnore
    private List<Projects> listprojects;
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


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Status status;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
