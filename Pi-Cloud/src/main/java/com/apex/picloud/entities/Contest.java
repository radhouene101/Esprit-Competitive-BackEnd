package com.apex.picloud.entities;

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
    @OneToMany(mappedBy = "contest")
    private List<Projects> projects;
    @ManyToOne
    private Option option;

    private String image;


}
