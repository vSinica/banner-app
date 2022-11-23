package ru.vados.BannerApp.Config;

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
@ComponentScan(basePackages = {"ru.vados.BannerApp.Controller", "ru.vados.BannerApp.Service"})
@EnableJpaRepositories(basePackages = "ru.vados.BannerApp.Repository")
@EntityScan(basePackages = {"ru.vados.BannerApp.Entity"})
public class ServiceConfig {

}
