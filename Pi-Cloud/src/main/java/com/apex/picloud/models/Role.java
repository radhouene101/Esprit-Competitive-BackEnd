package com.apex.picloud.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

// Constructors, getters, and setters
}
