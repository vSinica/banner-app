package ru.vados.BannerApp.test.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.vados.BannerApp.Config.ServiceConfig;


@SpringBootApplication(
        exclude = {
                org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration.class
        }
)
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"ru.vados.BannerApp.test"})
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@EnableAsync
@Import(ServiceConfig.class)
public class TestAppConfig {


}
