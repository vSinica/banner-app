package ru.vados.JpaTestWork.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.vados.JpaTestWork")
public class BannerApp {
    public static void main(String[] args) {SpringApplication.run(BannerApp.class, args);}
}
