package com.apex.picloud.controllers.radhouene;

import com.apex.picloud.dtos.radhouene.ProjectsDto;
import com.apex.picloud.services.IProjectsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("projects")
@AllArgsConstructor
@Tag(name = "projects")

public class ProjectsController {
    @Autowired
    IProjectsService service;



    @GetMapping
    public ResponseEntity<List<ProjectsDto>> getAllProjects() {
        return ResponseEntity
                .ok(service.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProjectsDto> getProjectById(@PathVariable Long id){
        return ResponseEntity
                .ok( service.findById(id));
    }
    @PostMapping
    public ResponseEntity<ProjectsDto> addProject(@RequestBody ProjectsDto p){
        return ResponseEntity
                .ok(service.save(p));
    }
   /* @PostMapping("/update")
    public ProjectsDto updateProject(@RequestBody ProjectsDto p){
        return service.updateProject(p);
    }*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){

        service.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/nominated")
    public ResponseEntity<List<ProjectsDto>> getNominatedProjects(){
        return ResponseEntity.ok(
                service.getIsNominated(true));
    }

    @GetMapping("/scolar-year/{scolarYear}")
    public ResponseEntity<List<ProjectsDto>> getAllWinnersByYear(@PathVariable String scolarYear){
        return ResponseEntity
                .ok(service.getAllwinnersByYear(scolarYear,true));
    }
    @GetMapping("/scolar-year")
    public ResponseEntity<List<ProjectsDto>> getAllWinners(){
        return ResponseEntity
                .ok(service.getAllWinners(true));
    }
    @GetMapping("/hall-of-fame-groups/{streakValue}")
    public ResponseEntity<List<String>> getGroupsByWinningSteak(@PathVariable Integer streakValue){
        List<ProjectsDto>  p;
        p= service.getGroupsByWinningSteak(streakValue);
        List<String> listOfGroupNames= new ArrayList<>();
        for (ProjectsDto pr:p){
            listOfGroupNames.add(pr.getGroupName());
        }
        return  ResponseEntity.ok(listOfGroupNames);
    }

    @PostMapping("/save-project/{optionId}/{categoryId}")
    public ResponseEntity<ProjectsDto> customSave(@PathVariable Long optionId,@PathVariable Long categoryId ,@RequestBody ProjectsDto dto) {
        return  ResponseEntity.ok(service.customSave(optionId,categoryId,dto));
    }
    @GetMapping("/find-projects-by-contestID/{contestId}")
    public ResponseEntity<List<ProjectsDto>> getProjectsByContest(Long contestId){
        return ResponseEntity.ok(service.getProjectsByContest(contestId));
    }
    @PatchMapping("/updateProject/{projectId}/{optionId}/{categoryId}")
    public ResponseEntity<ProjectsDto> updateProject(@PathVariable Long projectId,@PathVariable Long optionId,@PathVariable Long categoryId, @RequestBody ProjectsDto projectsDto){
       return ResponseEntity.ok(service.updateProject(projectId,optionId,categoryId,projectsDto));
    }
    @PatchMapping("voteUp/{projectId}/{userId}")
    public ResponseEntity<Boolean> voteUp(@PathVariable Long projectId, @PathVariable Long userId){
        return ResponseEntity.ok(service.voteUp(projectId,userId));
    }

    @PostMapping("add-image-to-project/{projectId}")
    public ResponseEntity<Void> uploadProjectImage(@PathVariable Long projectId, @RequestParam("file") MultipartFile file) throws IOException {
        try {
            service.uploadProjectImage(projectId, file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
