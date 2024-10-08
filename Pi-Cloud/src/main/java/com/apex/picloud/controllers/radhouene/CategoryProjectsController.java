package com.apex.picloud.controllers.radhouene;

import com.apex.picloud.dtos.radhouene.CategoryProjectsDto;
import com.apex.picloud.services.ICategoryProjectsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PUBLIC)
@RequestMapping("category-projects")
@Tag(name = "category")
public class CategoryProjectsController {
    @Autowired
    ICategoryProjectsService iCategoryProjectsService;
    @PostMapping
    public ResponseEntity<CategoryProjectsDto> addCategory(@RequestBody CategoryProjectsDto c){
        return ResponseEntity.ok(
                iCategoryProjectsService.save(c));
    }
    @GetMapping
    public ResponseEntity<List<CategoryProjectsDto>> getAllCategories(){
        return ResponseEntity.ok(
                iCategoryProjectsService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryProjectsDto> getCategoryById( @PathVariable  Long id ){
            return  ResponseEntity.ok(
                    iCategoryProjectsService.findById(id));
    }
    /*@PostMapping("/update")
    public CategoryProjects updateCategory(@RequestBody CategoryProjects c){
        return iCategoryProjectsService.updateCategory(c);
    }*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
         iCategoryProjectsService.delete(id);
         return ResponseEntity.ok().build();
    }

}
