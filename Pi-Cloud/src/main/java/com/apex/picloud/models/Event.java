package com.apex.picloud.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Entity
@Table(name="event")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(nullable = false)
    private String name;
    private String organizer;
    private String prize;
    private String location;
    private Double longitude;
    private Double lattitude;
    private String imageUrl;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="event")
    private Set<Group> groups;

    public Set<Group> getGroups() {
        return groups;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
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

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Event() {
    }

}
