package com.gentaliti.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;

@Configuration
public class JacksonConfig {

    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer());
        return builder;
    }
}
