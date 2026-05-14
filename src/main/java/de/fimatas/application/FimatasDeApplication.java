package de.fimatas.application;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;

@SpringBootApplication
@ComponentScan("de.fimatas")
@CommonsLog
public class FimatasDeApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FimatasDeApplication.class);
        app.addListeners((ApplicationListener<ApplicationStartedEvent>) event -> {
            log.info("Spring Boot Version: " + SpringBootVersion.getVersion());
            logBytecodeVersion();
        });
        app.run(args);
    }

    private static void logBytecodeVersion() {
        Class<?> c = MethodHandles.lookup().lookupClass();
        try (InputStream in = c.getResourceAsStream(c.getSimpleName() + ".class")) {
            if (in != null) {
                log.info("Java Bytecode Version: " + (in.readAllBytes()[7] - 44));
            } else {
                log.warn("Java Bytecode Version: unknown");
            }
        } catch (Exception e) {
            log.error("Java Bytecode Version: unknown", e);
        }
    }
}
