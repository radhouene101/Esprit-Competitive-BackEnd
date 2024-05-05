package com.apex.picloud.dtos.radhouene;

import com.apex.picloud.entities.*;
import com.apex.picloud.models.User;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectsDto {
    private Long id;
    private String name;
    private String groupName;
    private Boolean nominated;
    private Date date;
    private Integer numberOfVotes;
    private Integer groupStreak;
    private Boolean winner;
    private TypeNiveau niveau;
    private Option optionSpeciality;
    private CategoryProjects category;
    private String coach;
    private Boolean votingpool;
    private String scolarYear;
    private Long userId;
    private String classe;
    private Contest contest;
    private Set<User> voters;
    private String videoUrl;
    private String imageUrl;

    public static ProjectsDto fromEntity(Projects projects) {

        ProjectsDto dto = new ProjectsDto();
        dto.setId(projects.getId());
        dto.setName(projects.getName());
        dto.setGroupName(projects.getGroupName());
        dto.setNominated(projects.getNominated());
        dto.setClasse(projects.getClasse());
        dto.setDate(projects.getDate());
        dto.setNumberOfVotes(projects.getNumberOfVotes());
        dto.setUserId(projects.getUser() != null ? projects.getUser().getId() : null);
        dto.setGroupStreak(projects.getGroupStreak());
        dto.setWinner(projects.getWinner());
        dto.setNiveau(projects.getNiveau());
        dto.setVotingpool(projects.getVotingpool());
        dto.setScolarYear(projects.getScolarYear());
        dto.setCoach(projects.getCoach());
        dto.setCategory(projects.getCategory());
        dto.setOptionSpeciality(projects.getOptionSpeciality());
        dto.setVoters(projects.getVoters());
        dto.setVideoUrl(projects.getVideoUrl());
        dto.setImageUrl(projects.getImageUrl());
        // Avoiding circular reference here
        if (projects.getContest() != null) {
            dto.setContest(projects.getContest());
        }
        return dto;
    }

    public static Projects toEntity(ProjectsDto projectsDto){
        return Projects.builder()
                .id(projectsDto.getId())
                .user(User.builder()
                        .id(projectsDto.getUserId()).build())
                .name(projectsDto.getName())
                .classe(projectsDto.getClasse())
                .groupName(projectsDto.getGroupName())
                .nominated(projectsDto.getNominated())
                .date(projectsDto.getDate())
                .numberOfVotes(projectsDto.getNumberOfVotes())
                .groupStreak(projectsDto.getGroupStreak())
                .winner(projectsDto.getWinner())
                .niveau(projectsDto.getNiveau())
                .votingpool(projectsDto.getVotingpool())
                .scolarYear(projectsDto.getScolarYear())
                .coach(projectsDto.getCoach())
                .category(projectsDto.getCategory())
                .optionSpeciality(projectsDto.getOptionSpeciality())
                .contest(projectsDto.getContest())
                .voters(projectsDto.getVoters())
                .videoUrl(projectsDto.getVideoUrl())
                .imageUrl(projectsDto.getImageUrl())
                .build();
    }
}
