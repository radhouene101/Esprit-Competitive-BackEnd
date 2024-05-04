package com.apex.picloud.dtos.radhouene;

import com.apex.picloud.entities.Contest;
import com.apex.picloud.entities.Option;
import com.apex.picloud.entities.TypeNiveau;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ContestDto {

    private Long id;
    private String name;
    private String description;
    private TypeNiveau niveau;
    @JsonIdentityReference(alwaysAsId = true)
    @Nullable
    private List<ProjectsDto> projects;
    private Option option;
    private LocalDate deadline;
    private String image;

    public static ContestDto fromEntity(Contest contest){
        return ContestDto.builder()
                .id(contest.getId())
                .name(contest.getName())
                .description(contest.getDescription())
                .projects(contest.getProjects()
                        .stream()
                        .map(ProjectsDto::fromEntity)
                        .collect(Collectors.toList()))
                .option(contest.getOption())
                .niveau(contest.getNiveau())
                .deadline(contest.getDeadline())
                .image(contest.getImage())
                .build();
    }
    public static Contest toEntity(ContestDto contest){
        return Contest.builder()
                .id(contest.getId())
                .name(contest.getName())
                .description(contest.getDescription())
                .projects(contest.getProjects()
                        .stream()
                        .map(ProjectsDto::toEntity)
                        .collect(Collectors.toList()))
                .option(
                        Option.builder()
                                .id(contest.getOption().getId())
                                .build())
                .niveau(contest.getNiveau())
                .deadline(contest.getDeadline())
                .image(contest.getImage())
                .build();
    }



}
