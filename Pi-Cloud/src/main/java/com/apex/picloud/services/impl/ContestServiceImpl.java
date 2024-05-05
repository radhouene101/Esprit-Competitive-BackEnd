package com.apex.picloud.services.impl;


import com.apex.picloud.dtos.radhouene.ContestDto;
import com.apex.picloud.entities.Contest;
import com.apex.picloud.entities.Option;
import com.apex.picloud.entities.Projects;
import com.apex.picloud.repositories.ContestRepository;
import com.apex.picloud.repositories.OptionRepository;
import com.apex.picloud.repositories.ProjectsRepository;
import com.apex.picloud.services.IContestService;
import com.apex.picloud.validator.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContestServiceImpl implements IContestService {
    private static final Logger log = LoggerFactory.getLogger(ContestServiceImpl.class);
    @Autowired
    ContestRepository repository;
    @Autowired
    ProjectsRepository projectsRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    private final ObjectsValidator<ContestDto> validator;

    @Autowired
    private final ProjectsServiceImpl projectsService;
    @Autowired
    private ContestRepository contestRepository;

    @Override
    public ContestDto save(ContestDto dto) {
        log.info( "---------------" +dto.toString());
        validator.validate(dto);
        if(dto.getProjects()==null){
            dto.setProjects(new ArrayList<>());
        }
        Contest contest = ContestDto.toEntity(dto);

        repository.save(contest);

        return ContestDto.fromEntity(contest);
    }

    @Override
    public List<ContestDto> findAll() {
        return repository.findAll().stream()
                .map(ContestDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ContestDto findById(Long id) {
        return repository.findById(id)
                .map(ContestDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("no CONTEST-BAL DE PROJET exist with id :"+id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
        log.info(" contest with id "+id +" deleted" );
    }


    @Override
    public ContestDto customSaveContest(Long optionId, ContestDto contestDto) {

        if(contestDto.getProjects()==null){
            contestDto.setProjects(new ArrayList<>());
        }
        Option option = new Option();
        option = optionRepository.findById(optionId)
                .orElseThrow(() -> new EntityNotFoundException("no option with the provided ID exitst +  le id = " +optionId));
        contestDto.setOption(option);
        save(contestDto);

        return contestDto;

    }

    @Override
    @Transactional
    public ContestDto assignProjectToContest(Long contestDtoId, Long projectId) {
        Contest contest = contestRepository.findById(contestDtoId).orElseThrow(()-> new EntityNotFoundException("contest not found "));
        log.info("contest--------------" + contest.getId());
        Projects project = projectsRepository.findById(projectId)
                .orElseThrow(()-> new EntityNotFoundException("project not found"));
// Initialize the projects list if null
        List<Projects> projectsList = new ArrayList<>();
        projectsList = contest.getProjects();
        if(projectsList!=null) {
            projectsList.add(project);
        }
        contest.setProjects(projectsList);
        project.setContest(contest);
        projectsRepository.save(project);
        contestRepository.save(contest);

        return null;
    }

    @Override
    public ContestDto updateContest(Long id, ContestDto contestDto) {

        return null;
    }
}
