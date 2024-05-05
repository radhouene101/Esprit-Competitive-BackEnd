package com.apex.picloud.services.impl;

import com.apex.picloud.dtos.radhouene.ProjectsDto;
import com.apex.picloud.entities.CategoryProjects;
import com.apex.picloud.entities.Option;
import com.apex.picloud.entities.Projects;
import com.apex.picloud.entities.TypeNiveau;
import com.apex.picloud.models.User;
import com.apex.picloud.repositories.CategoryProjectsRepository;
import com.apex.picloud.repositories.OptionRepository;
import com.apex.picloud.repositories.ProjectsRepository;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.services.IProjectsService;
import com.apex.picloud.validator.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements IProjectsService {
    @Autowired
    private  final ProjectsRepository repository;
    @Autowired
    private  final OptionRepository optionRepository;

    @Autowired
    private final CategoryProjectsRepository categoryProjectsRepository;
    @Autowired
    private  final ObjectsValidator validator;
    @Autowired
    private UserRepository userRepository;


    @Override
    public ProjectsDto save(ProjectsDto dto) {
        validator.validate(dto);
        Projects project = ProjectsDto.toEntity(dto);
        repository.save(project);
        return ProjectsDto.fromEntity(project);
    }

    @Override
    public List<ProjectsDto> findAll() {
        return repository.findAll().stream()
                .map(ProjectsDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectsDto findById(Long id) {
        return repository.findById(id).map(ProjectsDto::fromEntity).orElseThrow(()-> new EntityNotFoundException("no project exist with id "+id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }


    @Override
    public List<ProjectsDto> getIsNominated(boolean b) {
        return repository.findAllByNominated(b)
                .stream()
                .map(ProjectsDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectsDto> currentlyNominated() {
        return null;
    }

    public List<ProjectsDto> getAllWinners(boolean b){
        return repository.findAllByWinner(b)
                .stream()
                .map(ProjectsDto::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public List<ProjectsDto> getAllwinnersByYear(String scolarYear,boolean b) {
        return repository.findAllByWinnerAndAndScolarYear(b,scolarYear)
                .stream()
                .map(ProjectsDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectsDto> getGroupsByWinningSteak(Integer streakValue) {
        return repository.findGroupByGroupStreakGreaterThanOrderByGroupStreak(streakValue)
                .stream()
                .map(ProjectsDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectsDto> getByCoach(String coach) {
        return null;
    }

    @Override
    public List<ProjectsDto> getAllByCategory(String category) {
        return null;
    }

    @Override
    public List<ProjectsDto> getAllByOptionSpeciality(String option) {
        return null;
    }

    @Override
    public List<ProjectsDto> getAllByNiveau(TypeNiveau niveau) {
        return null;
    }

    @Override
    public ProjectsDto customSave(Long optionId, Long categoryId , ProjectsDto dto) {
        Option option = new Option();
        option = optionRepository.findById(optionId).get();
        CategoryProjects categoryProjects = new CategoryProjects();
        categoryProjects = categoryProjectsRepository.findById(categoryId).get();
        dto.setCategory(categoryProjects);
        dto.setOptionSpeciality(option);
        save(dto);
        return dto;
    }

    public List<ProjectsDto> getProjectsByContest(Long contestId){
        return repository.findAllByContestId(contestId).stream()
                .map(ProjectsDto::fromEntity)
                .collect(Collectors.toList());
    }
    public ProjectsDto updateProject(Long projectId,ProjectsDto projectsDto){
        Projects p = repository.findById(projectId).get();

                p.setId(projectId);
                p.setDate(projectsDto.getDate());
                p.setName(projectsDto.getName());
                p.setGroupName(projectsDto.getGroupName());
                p.setCoach(projectsDto.getCoach());
                p.setClasse(projectsDto.getClasse());
                p.setNiveau(projectsDto.getNiveau());
                p.setOptionSpeciality(projectsDto.getOptionSpeciality());
                p.setWinner(projectsDto.isWinner());
                p.setScolarYear(projectsDto.getScolarYear());


        repository.save(p);
        return projectsDto ;
    }
    public Boolean voteUp(Long projectId,Long userId){
        Projects projects = repository.findById(projectId).
                orElseThrow(() -> new EntityNotFoundException("no project was found with this id"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("no User was found with this id"));
        if(projects.getVoters().contains(user)){
            return false;
        }
        projects.getVoters().add(user);
        projects.setNumberOfVotes(projects.getVoters().toArray().length);
        return true;
    }
}
