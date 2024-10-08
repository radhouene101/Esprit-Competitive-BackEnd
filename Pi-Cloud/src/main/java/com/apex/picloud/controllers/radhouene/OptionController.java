package com.apex.picloud.controllers.radhouene;

import com.apex.picloud.dtos.radhouene.OptionDto;
import com.apex.picloud.services.IOptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("options")
@Tag(name = "options")

public class OptionController {
    @Autowired
    IOptionService service;
    @GetMapping
    public ResponseEntity<List<OptionDto>> getAllOptions(){

        return ResponseEntity.ok(
        service.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<OptionDto> getOptionById(@PathVariable Long id){
        return ResponseEntity.ok(
                service.findById(id));
    }
    @PostMapping
    public ResponseEntity<OptionDto> addOption(@RequestBody  OptionDto o){
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
