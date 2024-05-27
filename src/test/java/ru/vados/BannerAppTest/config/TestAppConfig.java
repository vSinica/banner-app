package ru.vados.BannerAppTest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import ru.vados.BannerApp.BannerApp;


@ComponentScan(basePackages = {"ru.vados.BannerAppTest"})
@Import(BannerApp.class)
@PropertySource(value = {
        "classpath:config/application-test.yml"})
@ActiveProfiles("test")
public class TestAppConfig {


}
