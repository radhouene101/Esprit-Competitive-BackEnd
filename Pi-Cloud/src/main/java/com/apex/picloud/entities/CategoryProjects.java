package com.apex.picloud.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "category_project")
@Entity
public class CategoryProjects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoryProject")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToMany
    private List<SousCategory> sousCategories;
//    @OneToMany(mappedBy = "categoriesProjects",cascade = CascadeType.ALL)
//    private List<SousCategory> sousCategories;
}
