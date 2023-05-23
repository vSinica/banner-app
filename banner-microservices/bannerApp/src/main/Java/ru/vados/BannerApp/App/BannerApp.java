package ru.vados.BannerApp.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "ru.vados.BannerApp")
@EnableDiscoveryClient
public class BannerApp {
    public static void main(String[] args) {SpringApplication.run(BannerApp.class, args);}
}
