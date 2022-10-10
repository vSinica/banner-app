package ru.vados.JpaTestWork.Config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@ComponentScan(basePackages = {"ru.vados.JpaTestWork"})
@EnableTransactionManagement
@EnableAsync
public class AppConfig {


}
