package com.apex.picloud.services.impl;

import com.apex.picloud.dtos.radhouene.CategoryProjectsDto;
import com.apex.picloud.entities.CategoryProjects;
import com.apex.picloud.repositories.CategoryProjectsRepository;
import com.apex.picloud.services.ICategoryProjectsService;
import com.apex.picloud.validator.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryProjectsServiceImpl implements ICategoryProjectsService {
    @Autowired
    private  final CategoryProjectsRepository repository;
    @Autowired
    private final ObjectsValidator<CategoryProjectsDto> validator;

    // in each of these methods we always convert types from dto to the entity bch nkhaliw el abstraction and we follow the design pattern.
    @Override
    public CategoryProjectsDto save(CategoryProjectsDto dto) {
        validator.validate(dto);
        CategoryProjects categoryProjects = CategoryProjectsDto.toEntity(dto);
        repository.save(categoryProjects);
        return CategoryProjectsDto.fromEntity(categoryProjects);
    }

    @Override
    public List<CategoryProjectsDto> findAll() {
        return repository.findAll().stream()
                .map(CategoryProjectsDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryProjectsDto findById(Long id) {

        return repository.findById(id).map(CategoryProjectsDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("no category with the provided id : " + id));
    }

    @Override
    public void delete(Long id) {
        //check before delete

        repository.deleteById(id);
    }
}
