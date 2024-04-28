package com.apex.picloud.services.impl;

import com.apex.picloud.dtos.radhouene.SousCategoryDto;
import com.apex.picloud.entities.SousCategory;
import com.apex.picloud.repositories.SousCategoryRepository;
import com.apex.picloud.services.ISousCategoryService;
import com.apex.picloud.validator.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SousCategoryServiceImpl implements ISousCategoryService {
    @Autowired
    private final SousCategoryRepository repository;
    @Autowired
    private  final ObjectsValidator validator;


    @Override
    public SousCategoryDto save(SousCategoryDto dto) {
        validator.validate(dto);
        SousCategory sousCategory = SousCategoryDto.toEntity(dto);
        repository.save(sousCategory);
        return SousCategoryDto.fromEntity(sousCategory);
    }

    @Override
    public List<SousCategoryDto> findAll() {
        return repository.findAll().stream()
                .map(SousCategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public SousCategoryDto findById(Long id) {
        return repository.findById(id).map(SousCategoryDto::fromEntity).orElseThrow(()-> new EntityNotFoundException("no project exist with id "+id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }



}
