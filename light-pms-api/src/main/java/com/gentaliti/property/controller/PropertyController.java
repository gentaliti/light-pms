package com.gentaliti.property.controller;

import com.gentaliti.property.dto.PropertyDto;
import com.gentaliti.property.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping
    public List<PropertyDto> findAll() {
        return propertyService.findAll();
    }
}
