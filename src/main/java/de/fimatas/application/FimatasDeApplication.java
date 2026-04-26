package de.fimatas.application;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("de.fimatas")
@CommonsLog
public class FimatasDeApplication {
    public static void main(String[] args) {
        log.info("Spring Boot Version: " + SpringBootVersion.getVersion());
        SpringApplication.run(FimatasDeApplication.class, args);
    }
}
