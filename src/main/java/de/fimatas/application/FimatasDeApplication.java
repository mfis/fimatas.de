package de.fimatas.application;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("de.fimatas")
@CommonsLog
public class FimatasDeApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FimatasDeApplication.class);
        app.addListeners((ApplicationListener<ApplicationStartedEvent>) event -> {
            log.info("Spring Boot Version: " + SpringBootVersion.getVersion());
        });
        app.run(args);
    }
}
