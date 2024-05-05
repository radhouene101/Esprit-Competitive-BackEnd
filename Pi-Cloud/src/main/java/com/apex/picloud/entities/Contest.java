package com.apex.picloud.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private TypeNiveau niveau;
    private LocalDate deadline;
    @OneToMany(mappedBy = "contest",orphanRemoval = true,cascade = CascadeType.ALL)
    @Nullable
    @JsonManagedReference
    private List<Projects> projects;
    @ManyToOne
    private Option option;
    private String image;


}
