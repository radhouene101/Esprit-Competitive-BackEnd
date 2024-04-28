package com.apex.picloud.services.impl;

import com.apex.picloud.dtos.radhouene.OptionDto;
import com.apex.picloud.entities.Option;
import com.apex.picloud.repositories.OptionRepository;
import com.apex.picloud.services.IOptionService;
import com.apex.picloud.validator.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OptionServiceImpl implements IOptionService {
    @Autowired
    private final OptionRepository repository;
    @Autowired
    private final ObjectsValidator<OptionDto> validator;

    @Override
    public OptionDto save(OptionDto dto) {
        validator.validate(dto);
        Option option = OptionDto.toEntity(dto);
        repository.save(option);
        return OptionDto.fromEntity(option);
    }

    @Override
    public List<OptionDto> findAll() {
        return repository.findAll().stream()
                .map(OptionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public OptionDto findById(Long id) {
        return repository.findById(id)
                .map(OptionDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("no option exist with id :"+id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);

    }
}
