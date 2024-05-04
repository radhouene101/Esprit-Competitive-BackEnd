package com.apex.picloud.entities;

import com.apex.picloud.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


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
    private boolean nominated;
    @Column(name="submit_date")
    private Date date;
    private String classe;
    @Column(name="vote_number")
    private int numberOfVotes;
    @Column(name = "groupStreak")
    private int groupStreak;
    @Column(name="winner")
    private boolean winner;
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
    private boolean votingpool;
    private String scolarYear;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @ManyToOne(optional = true)
    @JoinColumn(name = "contest_id",nullable = true)
    @JsonIgnore
    private Contest contest;




}
