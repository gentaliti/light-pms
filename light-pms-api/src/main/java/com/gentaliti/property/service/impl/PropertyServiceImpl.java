package com.gentaliti.property.service.impl;

import com.gentaliti.property.domain.Property;
import com.gentaliti.property.domain.PropertyRepository;
import com.gentaliti.property.dto.PropertyDto;
import com.gentaliti.property.dto.PropertyDtoMapper;
import com.gentaliti.property.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;

    @Override
    public Property findById(Integer id) {
        return propertyRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public List<PropertyDto> findAll() {
        return propertyRepository.findAll()
                .stream()
                .map(PropertyDtoMapper::mapDto)
                .collect(Collectors.toList());
    }
}
