package ru.vados.BannerApp.Config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"ru.vados.BannerApp.Controller", "ru.vados.BannerApp.Service"})
@EnableJpaRepositories(basePackages = "ru.vados.BannerApp.Repository")
@EntityScan(basePackages = {"ru.vados.BannerApp.Entity"})
public class ServiceConfig {

}
