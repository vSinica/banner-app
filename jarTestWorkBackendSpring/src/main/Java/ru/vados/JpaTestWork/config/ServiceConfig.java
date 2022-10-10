package ru.vados.JpaTestWork.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ConfigurationPropertiesScan("ru.vados.JpaTestWork")
@ComponentScan(basePackages = {"ru.vados.JpaTestWork"})
@EnableJpaRepositories(basePackages = "ru.vados.JpaTestWork.Repository")
@EntityScan(basePackages = {"ru.vados.JpaTestWork.Entity"})
public class ServiceConfig {

    @Autowired
    void configureObjectMapper(final ObjectMapper mapper) {
        mapper.registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }

}
