package com.apex.picloud.dtos.radhouene;

import com.apex.picloud.models.User;
import com.apex.picloud.entities.CategoryProjects;
import com.apex.picloud.entities.Option;
import com.apex.picloud.entities.Projects;
import com.apex.picloud.entities.TypeNiveau;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProjectsDto {
    private Long id;
    private String name;
    private String groupName;
    private boolean nominated;
    private Date date;
    private int numberOfVotes;
    private int groupStreak;
    private boolean winner;
    private TypeNiveau niveau;
    private Option optionSpeciality;
    private CategoryProjects category;
    private String coach;
    private boolean votingpool;
    private String scolarYear;
    private Long userId;
    private String classe;

    public static ProjectsDto fromEntity(Projects projects){
        return ProjectsDto.builder()
                .id(projects.getId())
                .name(projects.getName())
                .groupName(projects.getGroupName())
                .nominated(projects.isNominated())
                .classe(projects.getClasse())
                .date(projects.getDate())
                .numberOfVotes(projects.getNumberOfVotes())
                .userId(projects.getUser().getId())
                .groupStreak(projects.getGroupStreak())
                .winner(projects.isWinner())
                .niveau(projects.getNiveau())
                .votingpool(projects.isVotingpool())
                .scolarYear(projects.getScolarYear())
                .coach(projects.getCoach())
                .category(projects.getCategory())
                .optionSpeciality(projects.getOptionSpeciality())
                .build();
    }
    public static Projects toEntity(ProjectsDto projects){
        return Projects.builder()
                .id(projects.getId())
                .user(User.builder()
                        .id(projects.getUserId()).build())
                .name(projects.getName())
                .classe(projects.getClasse())
                .groupName(projects.getGroupName())
                .nominated(projects.isNominated())
                .date(projects.getDate())
                .numberOfVotes(projects.getNumberOfVotes())
                .groupStreak(projects.getGroupStreak())
                .winner(projects.isWinner())
                .niveau(projects.getNiveau())
                .votingpool(projects.isVotingpool())
                .scolarYear(projects.getScolarYear())
                .coach(projects.getCoach())
                .category(projects.getCategory())
                .optionSpeciality(projects.getOptionSpeciality())
                .build();
    }
}
