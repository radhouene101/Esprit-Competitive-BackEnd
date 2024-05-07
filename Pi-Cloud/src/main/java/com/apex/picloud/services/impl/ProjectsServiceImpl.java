package com.apex.picloud.services.impl;

import com.apex.picloud.dtos.radhouene.ProjectsDto;
import com.apex.picloud.entities.*;
import com.apex.picloud.models.User;
import com.apex.picloud.repositories.CategoryProjectsRepository;
import com.apex.picloud.repositories.OptionRepository;
import com.apex.picloud.repositories.ProjectsRepository;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.services.IProjectsService;
import com.apex.picloud.validator.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements IProjectsService {
    private static final Logger log = LoggerFactory.getLogger(ProjectsServiceImpl.class);
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
//    @Override
//    public ProjectsDto saveWithImage(ProjectsDto dto,MultipartFile file) throws IOException {
//        validator.validate(dto);
//        Projects project = ProjectsDto.toEntity(dto);
//        uploadEventImage(project,file);
//        repository.save(project);
//        return ProjectsDto.fromEntity(project);
//    }

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
    public ProjectsDto updateProject(Long projectId,Long optionId,Long categoryId,ProjectsDto projectsDto){
        Option option = optionRepository.findById(optionId).get();
        CategoryProjects categoryProjects = categoryProjectsRepository.findById(categoryId).get();
        Projects p = repository.findById(projectId).get();
                p.setDate(projectsDto.getDate());
                p.setCategory(categoryProjects);
                p.setName(projectsDto.getName());
                p.setGroupName(projectsDto.getGroupName());
                p.setCoach(projectsDto.getCoach());
                p.setClasse(projectsDto.getClasse());
                p.setNiveau(projectsDto.getNiveau());
                p.setOptionSpeciality(option);
                p.setWinner(projectsDto.getWinner());
                p.setScolarYear(projectsDto.getScolarYear());
                p.setImageUrl(projectsDto.getImageUrl());
                p.setVideoUrl(projectsDto.getVideoUrl());


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
        projects.setNumberOfVotes(projects.getNumberOfVotes()+1);
        repository.save(projects);
        return true;
    }

    public void uploadProjectImage(Long projectid, MultipartFile file) throws IOException {
        Optional<Projects> projectget = repository.findById(projectid);
        if (projectget.isPresent()) {
            Projects projects = projectget.get();
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadDir = Paths.get("C:\\Users\\rberr\\OneDrive\\Documents\\PI-Arc-spring-angular\\Front-EndMerge\\Angular-Chat-App-FrontEnd\\src\\assets");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadDir.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                projects.setImageUrl("/images/" + fileName);
                repository.save(projects);
            } catch (IOException e) {
                throw new IOException("Could not save image: " + fileName, e);
            }
        } else {
            throw new IllegalArgumentException("Event with ID " + projectid + " not found");
        }
    }

    }
