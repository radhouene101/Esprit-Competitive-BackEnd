package com.apex.picloud.controllers.radhouene;

import com.apex.picloud.dtos.radhouene.ContestDto;
import com.apex.picloud.services.IContestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "contest-bal-de-projet",consumes = {"application/json"},produces = {"application/json"})
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
}
