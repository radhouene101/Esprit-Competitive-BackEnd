package com.apex.picloud.controllers.radhouene;

import com.apex.picloud.dtos.radhouene.SousCategoryDto;
import com.apex.picloud.services.ISousCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("sous-category")
@Tag(name = "sous-category")
public class SousCategoryController {
    @Autowired
    private final ISousCategoryService service;


    @GetMapping
    public ResponseEntity<List<SousCategoryDto>> getAllOptions(){

        return ResponseEntity.ok(
                service.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SousCategoryDto> getOptionById(@PathVariable Long id){
        return ResponseEntity.ok(
                service.findById(id));
    }
    @PostMapping
    public ResponseEntity<SousCategoryDto> addOption(@RequestBody  SousCategoryDto o){
        return ResponseEntity.ok(
                service.save(o));
    }
    /*@PostMapping("/update")
    public ResponseEntity<OptionDto> updateOption(@RequestBody OptionDto o){
        return service.updateOption(o);
    }
    */@DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteOptionById(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
