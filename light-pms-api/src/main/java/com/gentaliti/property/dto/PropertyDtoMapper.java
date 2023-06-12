package com.gentaliti.property.dto;

import com.gentaliti.property.domain.Property;

public class PropertyDtoMapper {
    private PropertyDtoMapper() {
    }

    public static PropertyDto mapDto(Property property) {
        return PropertyDto.builder()
                .id(property.getId())
                .name(property.getName())
                .build();
    }
}
