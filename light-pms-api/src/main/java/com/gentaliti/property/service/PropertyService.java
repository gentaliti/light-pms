package com.gentaliti.property.service;

import com.gentaliti.property.domain.Property;
import com.gentaliti.property.dto.PropertyDto;

import java.util.List;

public interface PropertyService {
    Property findById(Integer id);

    List<PropertyDto> findAll();
}
