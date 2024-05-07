package com.apex.picloud.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Entity
@Table(name="Team")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(nullable = false)
    private String name;
    private String Captain;

    public Group() {
    }

    @OneToMany(cascade = CascadeType.ALL)
    private Set<User> members;

    @ManyToOne
    Event event;
    public Group(Long id, String name, String captain) {
        this.id = id;
        this.name = name;
        Captain = captain;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaptain() {
        return Captain;
    }

    public void setCaptain(String captain) {
        Captain = captain;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }
}
