package com.apex.picloud.controllers.radhouene;

import com.apex.picloud.dtos.radhouene.ContestDto;
import com.apex.picloud.dtos.radhouene.ProjectsDto;
import com.apex.picloud.services.IContestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "contest-bal-de-projet")
@Tag(name = "contest-bal-de-projet")
public class ContestController {
    @Autowired
    IContestService service;

    @GetMapping
    public ResponseEntity<List<ContestDto>> getAllContests(){
        return ResponseEntity.ok(
                service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContestDto> getContestById(@PathVariable Long id){
        return ResponseEntity.ok(
                service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ContestDto> addContest(@RequestBody  ContestDto o){
        return ResponseEntity.ok(
                service.save(o));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteContestById(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/assign-project-to-contest/{contestDtoID}/{projectId}")
    public  ResponseEntity<Void> assignProjectToContest(@PathVariable  Long contestDtoID,@PathVariable Long projectId){
        service.assignProjectToContest(contestDtoID,projectId);
        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "save-contest/{optionId}",consumes = {"application/json"},produces = {"application/json"})
    public ResponseEntity<Void> customSaveContest(@PathVariable Long optionId,@RequestBody ContestDto contestDto) {
        service.customSaveContest(optionId,contestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("update/{id}/{optionId}")
    public ResponseEntity<ContestDto> updateContest( @PathVariable Long id, @RequestBody ContestDto contestDto,@PathVariable Long optionId){

        return ResponseEntity.ok(service.updateContest(id,contestDto,optionId));

    }
    @PatchMapping("/unassign-project-to-contest/{contestDtoID}/{projectId}")
    public ResponseEntity<Void> unAssignProjectToContest(@PathVariable  Long contestDtoID,@PathVariable Long projectId){
        service.unAssignProjectToContest(contestDtoID,projectId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getWinnerByContest/{contestID}")
    public ResponseEntity<ProjectsDto> getProjectWinnerByContest(@PathVariable Long contestID){
        return ResponseEntity.ok(service.getProjectWinnerByContest(contestID));
    }
}
