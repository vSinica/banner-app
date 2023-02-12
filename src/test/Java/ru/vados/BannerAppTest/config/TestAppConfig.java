package ru.vados.BannerAppTest.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.vados.BannerApp.Config.ServiceConfig;
import ru.vados.BannerApp.Config.YamlPropertySourceFactory;


@SpringBootApplication(
        exclude = {
                org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration.class
        }
)
@EnableTransactionManagement
@ComponentScan(basePackages = {"ru.vados.BannerAppTest"})
@PropertySource(value = {
        "classpath:config/application-test.yml"}, factory = YamlPropertySourceFactory.class)
@EnableAsync
@Import({ServiceConfig.class})
@ActiveProfiles("test")
public class TestAppConfig {


}
