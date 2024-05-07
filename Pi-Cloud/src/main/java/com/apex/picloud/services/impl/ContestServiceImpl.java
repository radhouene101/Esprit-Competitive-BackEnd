package com.apex.picloud.services.impl;


import com.apex.picloud.dtos.radhouene.ContestDto;
import com.apex.picloud.dtos.radhouene.ProjectsDto;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    @Transactional
    public ContestDto unAssignProjectToContest(Long contestDtoId, Long projectId) {
        Contest contest = contestRepository.findById(contestDtoId).orElseThrow(()-> new EntityNotFoundException("contest not found "));
        log.info("contest--------------" + contest.getId());
        Projects project = projectsRepository.findById(projectId)
                .orElseThrow(()-> new EntityNotFoundException("project not found"));
// Initialize the projects list if null
        List<Projects> projectsList = new ArrayList<>();
        projectsList = contest.getProjects();
        for (Projects p : projectsList){
            if(p==project){
                projectsList.remove(p);
                break;
            }
        }
        if(projectsList!=null) {
            projectsList.add(project);
        }
        contest.setProjects(projectsList);
        project.setContest(null);
        projectsRepository.save(project);
        contestRepository.save(contest);

        return null;
    }


    @Override
    public ContestDto updateContest(Long id, ContestDto contestDto, Long optionId) {
        Option  option =optionRepository.findById(optionId).get();
        Contest contest = contestRepository.findById(id).get();
        contest.setOption(option);
        contest.setProjects(contestDto.getProjects());
        contest.setName(contestDto.getName());
        contest.setImage(contestDto.getImage());
        contest.setNiveau(contestDto.getNiveau());
        contest.setDeadline(contestDto.getDeadline());
        contest.setOption(contestDto.getOption());
        contest.setDescription(contestDto.getDescription());
        optionRepository.save(option);
        return save(ContestDto.fromEntity(contest));
    }

    public void  stopContestAndAssignWinner(){
        List<Contest> contests = contestRepository.findAll();
        for (Contest c : contests){
            if(c.getDeadline()!= null && LocalDateTime.now().isAfter(c.getDeadline())){
                c.setAllowVote(false);
                List<ProjectsDto> projectsDtoList = projectsService.getProjectsByContest(c.getId());
                Integer minValue=Integer.MIN_VALUE;
                Long winnerId =projectsDtoList.get(1).getId();
                for (ProjectsDto p : projectsDtoList){
                    if (p.getNumberOfVotes()>minValue){
                        winnerId=p.getId();
                    }
                }
                //settting the winenr
                Projects projects = projectsRepository.findById(winnerId).orElseThrow(()-> new EntityNotFoundException("project not found"));
                projects.setWinner(true);

            }
        }
    }
    @Scheduled(fixedDelay = 60000)
    public void scheduleContestDeadline(){
        stopContestAndAssignWinner();
    }

   /* public ProjectsDto setProjectWinnerByContest(Long contestID){
        List<ProjectsDto> projectsDtoList = projectsService.getProjectsByContest(contestID);
        Integer minValue=Integer.MIN_VALUE;
        Long winnerId =projectsDtoList.get(1).getId();
        for (ProjectsDto p : projectsDtoList){
            if (p.getNumberOfVotes()>minValue){
                winnerId=p.getId();
            }
        }
        //stoping the vote
        Contest contest = contestRepository.findById(contestID).get();
        contest.setAllowVote(false);
        //settting the winenr
        Projects projects = projectsRepository.findById(winnerId).orElseThrow(()-> new EntityNotFoundException("project not found"));
        projects.setWinner(true);
        return ProjectsDto.fromEntity(projects);
    }*/

    public ProjectsDto getProjectWinnerByContest(Long contestID){
        List<ProjectsDto> projectsDtoList = projectsService.getProjectsByContest(contestID);
        for (ProjectsDto p : projectsDtoList){
            if(p.getWinner()){
                return ProjectsDto.fromEntity(projectsRepository.findById(p.getId()).get());
            }
        }
        return null;
    }
}
