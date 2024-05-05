package com.apex.picloud.entities;

import com.apex.picloud.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Projects  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idProject")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="group_name")
    private String groupName;
    @Column(name="nominated")
    private Boolean nominated;
    @Column(name="submit_date")
    private Date date;
    private String classe;
    @Column(name="vote_number")
    private Integer numberOfVotes;
    @Column(name = "groupStreak")
    private Integer groupStreak;
    @Column(name="winner")
    private Boolean winner;
    @Enumerated(EnumType.STRING)
    private TypeNiveau niveau;
    @ManyToOne
    @JoinColumn(name = "option_id")
    @JsonIgnore
    private Option optionSpeciality;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private CategoryProjects category;
    private String coach;
    private Boolean votingpool;
    private String scolarYear;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @ManyToOne(optional = true)
    @JoinColumn(name = "contest_id",nullable = true)
    @JsonBackReference
    private Contest contest;
    @ManyToMany
    private Set<User> voters;
    private String videoUrl;
    private String imageUrl;




}
