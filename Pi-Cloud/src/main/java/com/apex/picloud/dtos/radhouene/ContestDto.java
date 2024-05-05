package com.apex.picloud.dtos.radhouene;

import com.apex.picloud.entities.Contest;
import com.apex.picloud.entities.Option;
import com.apex.picloud.entities.Projects;
import com.apex.picloud.entities.TypeNiveau;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContestDto {

    private Long id;
    private String name;
    private String description;
    private TypeNiveau niveau;
    @JsonIdentityReference(alwaysAsId = true)
    @Nullable
    private List<Projects> projects;
    private Option option;
    private LocalDate deadline;
    private String image;

    public static ContestDto fromEntity(Contest contest) {
        if (contest == null) {
            return null;
        }
        ContestDto dto = new ContestDto();
        dto.setId(contest.getId());
        dto.setName(contest.getName());
        dto.setDescription(contest.getDescription());
        dto.setOption(contest.getOption());
        dto.setNiveau(contest.getNiveau());
        dto.setDeadline(contest.getDeadline());
        dto.setImage(contest.getImage());
        // Avoiding infinite recursion for projects
        dto.setProjects(contest.getProjects());

        return dto;
    }

    public static Contest toEntity(ContestDto contest){
        return Contest.builder()
                .id(contest.getId())
                .name(contest.getName())
                .description(contest.getDescription())
                .projects(contest.getProjects())
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
