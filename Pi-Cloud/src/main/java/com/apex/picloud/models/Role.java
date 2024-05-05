package com.apex.picloud.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    //@JsonIgnoreProperties("user_roles")
    private Set<User> user_roles = new HashSet<>();
    private String name;
    public Role() {

    }


    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
// Constructors, getters, and setters
}
